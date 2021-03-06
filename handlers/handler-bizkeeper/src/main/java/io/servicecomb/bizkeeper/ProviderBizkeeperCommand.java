/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.bizkeeper;

import io.servicecomb.core.Invocation;
import io.servicecomb.swagger.invocation.Response;
import io.servicecomb.swagger.invocation.exception.ExceptionFactory;
import io.servicecomb.swagger.invocation.exception.InvocationException;

public class ProviderBizkeeperCommand extends BizkeeperCommand {
    protected ProviderBizkeeperCommand(String type, Invocation invocation,
            com.netflix.hystrix.HystrixObservableCommand.Setter setter) {
        super(type, invocation, setter);
    }

    @Override
    protected boolean isFailedResponse(Response resp) {
        if (resp.isFailed()) {
            if (InvocationException.class.isInstance(resp.getResult())) {
                InvocationException e = (InvocationException) resp.getResult();
                return e.getStatusCode() == ExceptionFactory.PRODUCER_INNER_STATUS_CODE;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
