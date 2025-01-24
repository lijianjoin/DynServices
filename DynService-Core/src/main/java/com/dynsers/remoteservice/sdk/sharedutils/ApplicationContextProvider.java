/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.sdk.sharedutils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class provides the ApplicationContext to other classes. It is used to get the ApplicationContext in classes that are not managed by Spring.
 *
 * <p>
 * IMPORTANT: This class should not be used in classes that are managed by Spring. Instead, the ApplicationContext should be injected via the constructor. All
 * new classes MUST be managed by spring, this class is only used in legacy code.
 * </p>
 *
 */
@Component("applicationContextProvider")
public class ApplicationContextProvider implements ApplicationContextAware {

    /**
     * This method is called from the ApplicationContext when the bean is created. SuppressWarnings because "Make the enclosing method "static" or remove this
     * set" is not possible here.
     *
     * @param applicationContext The ApplicationContext that is being set.
     * @throws BeansException If the bean cannot be created.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.setContext(applicationContext);
    }
}