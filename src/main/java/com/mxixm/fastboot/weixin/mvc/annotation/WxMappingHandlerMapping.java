/*
 * Copyright (c) 2016-2017, Guangshan (guangshan1992@qq.com) and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mxixm.fastboot.weixin.mvc.annotation;

import com.mxixm.fastboot.weixin.annotation.*;
import com.mxixm.fastboot.weixin.module.Wx;
import com.mxixm.fastboot.weixin.module.event.WxEvent;
import com.mxixm.fastboot.weixin.module.menu.WxButtonItem;
import com.mxixm.fastboot.weixin.module.menu.WxMenuManager;
import com.mxixm.fastboot.weixin.module.message.WxMessage;
import com.mxixm.fastboot.weixin.module.message.support.WxAsyncMessageTemplate;
import com.mxixm.fastboot.weixin.module.web.WxRequest;
import com.mxixm.fastboot.weixin.module.web.session.WxSessionManager;
import com.mxixm.fastboot.weixin.mvc.converter.WxXmlMessageConverter;
import com.mxixm.fastboot.weixin.mvc.method.WxAsyncHandlerFactory;
import com.mxixm.fastboot.weixin.mvc.method.WxMappingHandlerMethodNamingStrategy;
import com.mxixm.fastboot.weixin.mvc.method.WxMappingInfo;
import com.mxixm.fastboot.weixin.service.WxBuildinVerifyService;
import com.mxixm.fastboot.weixin.util.WildcardUtils;
import com.mxixm.fastboot.weixin.util.WxWebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * FastBootWeixin WxMappingHandlerMapping
 *
 * @author Guangshan
 * @date 2017/09/21 23:45
 * @since 0.1.2
 */
public class WxMappingHandlerMapping extends AbstractHandlerMethodMapping<WxMappingInfo> implements InitializingBean {

    /**
     * http://blog.csdn.net/Mr_SeaTurtle_/article/details/52992207
     */
    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";

    private static final ParamsRequestCondition WX_VERIFY_PARAMS_CONDITION = new ParamsRequestCondition("echostr", "nonce", "signature", "timestamp");

    private static final ParamsRequestCondition WX_POST_PARAMS_CONDITION = new ParamsRequestCondition("nonce", "signature", "timestamp");

    private static final ConsumesRequestCondition WX_POST_CONSUMES_CONDITION = new ConsumesRequestCondition(MediaType.TEXT_XML_VALUE);

    private static final Method WX_VERIFY_METHOD = ClassUtils.getMethod(WxBuildinVerifyService.class, "verify", (Class<?>[]) null);

    private final HandlerMethod wxVerifyMethodHandler;

    private final HandlerMethod defaultHandlerMethod;

    private static final Method SUPPLIER_METHOD = ClassUtils.getMethod(Supplier.class, "get");

    /**
     * mappingRegistry同父类完全不同，故自己创建一个
     * 也因为此，要把父类所有使用mappingRegistry的地方覆盖父类方法
     */
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    private final String path;

    /**
     * 可以加一个开关功能:已经加了
     */
    private final WxMenuManager wxMenuManager;

    private final WxSessionManager wxSessionManager;

    private final WxAsyncHandlerFactory wxAsyncHandlerFactory;

    private final WxXmlMessageConverter wxXmlMessageConverter;

    public WxMappingHandlerMapping(String path, WxBuildinVerifyService wxBuildinVerifyService, WxMenuManager wxMenuManager,
                                   WxSessionManager wxSessionManager, WxAsyncMessageTemplate wxAsyncMessageTemplate,
                                   WxXmlMessageConverter wxXmlMessageConverter) {
        super();
        this.path = (path.startsWith("/") ? "" : "/") + path;
        this.wxVerifyMethodHandler = new HandlerMethod(wxBuildinVerifyService, WX_VERIFY_METHOD);
        this.wxMenuManager = wxMenuManager;
        this.wxSessionManager = wxSessionManager;
        this.wxAsyncHandlerFactory = new WxAsyncHandlerFactory(wxAsyncMessageTemplate);
        this.defaultHandlerMethod = new HandlerMethod((Supplier)(() -> HttpEntity.EMPTY), SUPPLIER_METHOD);
        this.setHandlerMethodMappingNamingStrategy(new WxMappingHandlerMethodNamingStrategy());
        this.wxXmlMessageConverter = wxXmlMessageConverter;
    }

    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
        // 只接受根目录的请求
        if (!path.equals(lookupPath)) {
            if (logger.isDebugEnabled()) {
                logger.debug("path not match with wxHandlerMapping, path is" + lookupPath);
            }
            return null;
        }
        if (isWxVerifyRequest(request)) {
            return wxVerifyMethodHandler;
        }
        if (isWxPostRequest(request)) {
            WxRequest wxRequest = new WxRequest(request, wxSessionManager);
            WxWebUtils.setWxRequestToRequest(request, wxRequest);
            wxRequest.setBody(wxXmlMessageConverter.read(request));
            final HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);
            return handlerMethod != null ? handlerMethod : defaultHandlerMethod;
        }
        return null;
    }

    /**
     * 判断是否是微信verify请求
     */
    private boolean isWxVerifyRequest(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod())
                && WX_VERIFY_PARAMS_CONDITION.getMatchingCondition(request) != null;
    }

    /**
     * 判断是否是微信POST请求
     */
    private boolean isWxPostRequest(HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod())
                && WX_POST_PARAMS_CONDITION.getMatchingCondition(request) != null
                && WX_POST_CONSUMES_CONDITION.getMatchingCondition(request) != null;
    }

    @Override
    public Map<WxMappingInfo, HandlerMethod> getHandlerMethods() {
        this.mappingRegistry.acquireReadLock();
        try {
            return Collections.unmodifiableMap(this.mappingRegistry.getMappings());
        } finally {
            this.mappingRegistry.releaseReadLock();
        }
    }

    @Override
    public List<HandlerMethod> getHandlerMethodsForMappingName(String mappingName) {
        return this.mappingRegistry.getHandlerMethodsByMappingName(mappingName);
    }

    MappingRegistry getMappingRegistry() {
        return this.mappingRegistry;
    }

    @Override
    public void registerMapping(WxMappingInfo mapping, Object handler, Method method) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    @Override
    public void unregisterMapping(WxMappingInfo mapping) {
        this.mappingRegistry.unregister(mapping);
    }

    /**
     * 父类中只有getHandlerInternal方法有使用
     */
    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        this.mappingRegistry.acquireReadLock();
        try {
            HandlerMethod handlerMethod = null;
            WxRequest.Body wxRequestBody = WxWebUtils.getWxRequestBodyFromRequest(request);
            // switch不被推荐缺少default
            switch (wxRequestBody.getCategory()) {
                case BUTTON:
                    handlerMethod = lookupButtonHandlerMethod(wxRequestBody);
                    break;
                case EVENT:
                    handlerMethod = lookupEventHandlerMethod(wxRequestBody);
                    break;
                case MESSAGE:
                    handlerMethod = lookupMessageHandlerMethod(wxRequestBody);
                    break;
                default:
                    break;
            }
            handleMatch(handlerMethod, request);
            if (logger.isDebugEnabled() && handlerMethod != null) {
                logger.debug("find match wx handler, handler is " + handlerMethod);
            }
            return handlerMethod;
        } finally {
            this.mappingRegistry.releaseReadLock();
        }
    }

    protected void handleMatch(HandlerMethod handlerMethod, HttpServletRequest request) {
        // 返回XML
        if (handlerMethod != null) {
            request.setAttribute(PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, Collections.singleton(MediaType.TEXT_XML));
        }
    }

    private HandlerMethod lookupButtonHandlerMethod(WxRequest.Body wxRequestBody) {
        HandlerMethod handlerMethod = mappingRegistry.getHandlerButtonByEventKey(wxRequestBody.getEventKey());
        if (handlerMethod == null) {
            handlerMethod = mappingRegistry.getHandlerButtonByButtonType(wxRequestBody.getButtonType());
        }
        return handlerMethod;
    }

    private HandlerMethod lookupEventHandlerMethod(WxRequest.Body wxRequestBody) {
        return mappingRegistry.getHandlerEventByEventType(wxRequestBody.getEventType());
    }

    private HandlerMethod lookupMessageHandlerMethod(WxRequest.Body wxRequestBody) {
        if (wxRequestBody.getMessageType() == WxMessage.Type.TEXT) {
            List<HandlerMethod> handlerMethods = mappingRegistry.getHandlersByContent(wxRequestBody.getContent());
            if (!handlerMethods.isEmpty()) {
                return handlerMethods.get(0);
            } else {
                // 如果没有匹配的时候，返回空。因为默认的wildcard是*所以默认情况肯定是有匹配的
                return null;
            }
        }
        return mappingRegistry.getHandlerMessageByMessageType(wxRequestBody.getMessageType());
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, WxController.class);
    }

    @Override
    protected void registerHandlerMethod(Object handler, Method method, WxMappingInfo mapping) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    @Override
    protected Set<String> getMappingPathPatterns(WxMappingInfo info) {
        return Collections.singleton("/");
    }

    @Override
    protected WxMappingInfo getMatchingMapping(WxMappingInfo mapping, HttpServletRequest request) {
        return mapping.getMatchingCondition(request);
    }

    @Override
    protected Comparator<WxMappingInfo> getMappingComparator(HttpServletRequest request) {
        return (info1, info2) -> info1.compareTo(info2, request);
    }

    @Override
    protected WxMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        WxMappingInfo info = createWxMappingInfo(method);
        if (info != null) {
            WxMappingInfo typeInfo = createWxMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    @Override
    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        if (handler instanceof String) {
            String beanName = (String) handler;
            handler = this.getApplicationContext().getAutowireCapableBeanFactory().getBean(beanName);
        }
        if (AnnotatedElementUtils.hasAnnotation(method, WxAsyncMessage.class) || AnnotatedElementUtils.hasAnnotation(handler.getClass(), WxAsyncMessage.class)) {
            return new HandlerMethod(wxAsyncHandlerFactory.createHandler(handler), method);
        } else {
            return new HandlerMethod(handler, method);
        }
    }

    private WxMappingInfo createWxMappingInfo(AnnotatedElement element) {
        WxButton wxButton = AnnotatedElementUtils.findMergedAnnotation(element, WxButton.class);
        // 由于这个机制，所以无法为同一个方法绑定多个WxButton、WxEventMapping、WxMessageMapping
        if (wxButton != null) {
            return createWxButtonMappingInfo(wxButton);
        }
        WxMessageMapping wxMessageMapping = AnnotatedElementUtils.findMergedAnnotation(element, WxMessageMapping.class);
        if (wxMessageMapping != null) {
            return createWxMessageMappingInfo(wxMessageMapping);
        }
        WxEventMapping wxEventMapping = AnnotatedElementUtils.findMergedAnnotation(element, WxEventMapping.class);
        if (wxEventMapping != null) {
            return createWxEventMappingInfo(wxEventMapping);
        }
        return null;
    }

    private WxMappingInfo createWxButtonMappingInfo(WxButton wxButton) {
        // 在这里加上菜单管理是否启用的判断
        WxButtonItem wxButtonItem = wxMenuManager.add(wxButton);
        return WxMappingInfo
                .category(Wx.Category.BUTTON)
                // eventKey是url，如果类型是VIEW的话
                // 在builder中已处理
                .eventKey(wxButtonItem.getKey())
                .mappingName(wxButtonItem.getName())
                .buttonTypes(wxButtonItem.getType())
                .build();
    }

    private WxMappingInfo createWxMessageMappingInfo(WxMessageMapping wxMessageMapping) {
        return WxMappingInfo
                .category(Wx.Category.MESSAGE)
                .messageTypes(wxMessageMapping.type())
                .mappingName(wxMessageMapping.name())
                .wildcards(wxMessageMapping.wildcard())
                .build();
    }

    private WxMappingInfo createWxEventMappingInfo(WxEventMapping wxEventMapping) {
        return WxMappingInfo
                .category(Wx.Category.EVENT)
                .eventTypes(wxEventMapping.type())
                .mappingName(wxEventMapping.name())
                .build();
    }

    class MappingRegistry {

        private final Map<WxMappingInfo, MappingRegistration<WxMappingInfo>> registry = new HashMap<>();

        private final Map<WxMappingInfo, HandlerMethod> mappingLookup = new LinkedHashMap<>();

        private final Map<String, HandlerMethod> eventKeyLookup = new LinkedHashMap<>();

        private final Map<WxButton.Type, HandlerMethod> buttonTypeLookup = new LinkedHashMap<>();

        private final Map<WxEvent.Type, HandlerMethod> eventTypeLookup = new LinkedHashMap<>();

        private final Map<WxMessage.Type, HandlerMethod> messageTypeLookup = new LinkedHashMap<>();

        private final MultiValueMap<Wx.Category, WxMappingInfo> categoryLookup = new LinkedMultiValueMap<>();

        private final Map<String, List<HandlerMethod>> nameLookup = new ConcurrentHashMap<>();

        private final MultiValueMap<String, WxMappingInfo> wildcardLookup = new LinkedMultiValueMap<>();

        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public Map<WxMappingInfo, HandlerMethod> getMappings() {
            return this.mappingLookup;
        }

        public HandlerMethod getHandlerButtonByEventKey(String eventKey) {
            return this.eventKeyLookup.get(eventKey);
        }

        public HandlerMethod getHandlerButtonByButtonType(WxButton.Type buttonType) {
            return this.buttonTypeLookup.get(buttonType);
        }

        public HandlerMethod getHandlerEventByEventType(WxEvent.Type eventType) {
            return this.eventTypeLookup.get(eventType);
        }

        public HandlerMethod getHandlerMessageByMessageType(WxMessage.Type messageType) {
            return this.messageTypeLookup.get(messageType);
        }

        public List<HandlerMethod> getHandlerMethodsByMappingName(String mappingName) {
            return this.nameLookup.get(mappingName);
        }

        public List<HandlerMethod> getHandlersByContent(String content) {
            List<String> matches = this.wildcardLookup.keySet().stream().filter(w -> WildcardUtils.wildcardMatch(content, w))
                    .sorted(Comparator.comparing(String::length).reversed()).collect(Collectors.toList());
            if (matches.isEmpty()) {
                return Collections.emptyList();
            }
            // 因为有*通配符的是，也会作为长度1进行判断，会和其他长度为1的比如单字符匹配的冲突，所以这里移除匹配结果里的*
            if (matches.size() > 1 && matches.contains(WxMessageMapping.MATCH_ALL_WILDCARD)) {
                // 有可能从列表中存在多个*通配符，这样使用时不合法的，应该最多存在一个*通配符
                matches.remove("*");
            }
            if (matches.size() > 1) {
                if (matches.get(0).length() == matches.get(1).length()) {
                    logger.error("有两个重复的通配符！以后加入通配符权重！！");
                }
            }
            String selectedMatch = matches.get(0);
            final List<HandlerMethod> handlerMethods = this.wildcardLookup.get(selectedMatch).stream().map(w -> mappingLookup.get(w)).collect(Collectors.toList());
            if (handlerMethods.size() > 1) {
                logger.error("有一个通配符有两个匹配的方法！");
            }
            return handlerMethods;
        }

        public void acquireReadLock() {
            this.readWriteLock.readLock().lock();
        }

        public void releaseReadLock() {
            this.readWriteLock.readLock().unlock();
        }

        public void register(WxMappingInfo mapping, Object handler, Method method) {
            this.readWriteLock.writeLock().lock();
            try {
                HandlerMethod handlerMethod = createHandlerMethod(handler, method);
                assertUniqueMethodMapping(handlerMethod, mapping);

                if (logger.isInfoEnabled()) {
                    logger.info("Mapped \"" + mapping + "\" onto " + handlerMethod);
                }
                this.mappingLookup.put(mapping, handlerMethod);

                this.categoryLookup.add(mapping.getCategory(), mapping);

                if (!StringUtils.isEmpty(mapping.getEventKey())) {
                    eventKeyLookup.put(mapping.getEventKey(), handlerMethod);
                }
                // 对于button类型，暂时只支持key查找
                if (!mapping.getWxEventTypeCondition().isEmpty()) {
                    mapping.getWxEventTypeCondition().getEnums().forEach(
                            e -> eventTypeLookup.put(e, handlerMethod)
                    );
                }
                if (!mapping.getWxMessageTypeCondition().isEmpty()) {
                    mapping.getWxMessageTypeCondition().getEnums().forEach(
                            e -> messageTypeLookup.put(e, handlerMethod)
                    );
                }
                if (!mapping.getWxButtonTypeCondition().isEmpty()) {
                    mapping.getWxButtonTypeCondition().getEnums().forEach(
                            e -> buttonTypeLookup.put(e, handlerMethod)
                    );
                }
                if (!mapping.getWxMessageWildcardCondition().isEmpty()) {
                    mapping.getWxMessageWildcardCondition().getWildcards().forEach(
                            w -> wildcardLookup.add(w, mapping)
                    );
                }
                String name = null;
                if (getNamingStrategy() != null) {
                    name = getNamingStrategy().getName(handlerMethod, mapping);
                    addMappingName(name, handlerMethod);
                }
                this.registry.put(mapping, new MappingRegistration<>(mapping, handlerMethod, name));
            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        }

        private void assertUniqueMethodMapping(HandlerMethod newHandlerMethod, WxMappingInfo mapping) {
            HandlerMethod handlerMethod = this.mappingLookup.get(mapping);
            if (handlerMethod != null && !handlerMethod.equals(newHandlerMethod)) {
                throw new IllegalStateException(
                        "Ambiguous mapping. Cannot map '" + newHandlerMethod.getBean() + "' method \n" +
                                newHandlerMethod + "\nto " + mapping + ": There is already '" +
                                handlerMethod.getBean() + "' bean method\n" + handlerMethod + " mapped.");
            }
        }

        private void addMappingName(String name, HandlerMethod handlerMethod) {
            List<HandlerMethod> oldList = this.nameLookup.get(name);
            if (oldList == null) {
                oldList = Collections.<HandlerMethod>emptyList();
            }

            for (HandlerMethod current : oldList) {
                if (handlerMethod.equals(current)) {
                    return;
                }
            }

            if (logger.isTraceEnabled()) {
                logger.trace("Mapping value '" + name + "'");
            }

            List<HandlerMethod> newList = new ArrayList<HandlerMethod>(oldList.size() + 1);
            newList.addAll(oldList);
            newList.add(handlerMethod);
            this.nameLookup.put(name, newList);

            if (newList.size() > 1) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Mapping value clash for handlerMethods " + newList +
                            ". Consider assigning explicit names.");
                }
            }
        }

        public void unregister(WxMappingInfo mapping) {
            this.readWriteLock.writeLock().lock();
            try {
                MappingRegistration<WxMappingInfo> definition = this.registry.remove(mapping);
                if (definition == null) {
                    return;
                }

                this.mappingLookup.remove(definition.getMapping());

                if (mapping.getEventKey() != null) {
                    eventKeyLookup.remove(mapping.getEventKey());
                }

                removeMappingName(definition);

            } finally {
                this.readWriteLock.writeLock().unlock();
            }
        }

        private void removeMappingName(MappingRegistration<WxMappingInfo> definition) {
            String name = definition.getMappingName();
            if (name == null) {
                return;
            }
            HandlerMethod handlerMethod = definition.getHandlerMethod();
            List<HandlerMethod> oldList = this.nameLookup.get(name);
            if (oldList == null) {
                return;
            }
            if (oldList.size() <= 1) {
                this.nameLookup.remove(name);
                return;
            }
            List<HandlerMethod> newList = new ArrayList<HandlerMethod>(oldList.size() - 1);
            for (HandlerMethod current : oldList) {
                if (!current.equals(handlerMethod)) {
                    newList.add(current);
                }
            }
            this.nameLookup.put(name, newList);
        }
    }

    private static class MappingRegistration<T> {

        private final T mapping;

        private final HandlerMethod handlerMethod;

        private final String mappingName;

        public MappingRegistration(T mapping, HandlerMethod handlerMethod, String mappingName) {
            Assert.notNull(mapping, "Mapping must not be null");
            Assert.notNull(handlerMethod, "HandlerMethod must not be null");
            this.mapping = mapping;
            this.handlerMethod = handlerMethod;
            this.mappingName = mappingName;
        }

        public T getMapping() {
            return this.mapping;
        }

        public HandlerMethod getHandlerMethod() {
            return this.handlerMethod;
        }

        public String getMappingName() {
            return this.mappingName;
        }
    }

}
