/*
 * Copyright © 2018 Apple Inc. and the ServiceTalk project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicetalk.dns.discovery.netty;

import io.netty.resolver.ResolvedAddressTypes;
import io.netty.resolver.dns.DnsNameResolverBuilder;

/**
 * Defined resolved address types.
 */
public enum DnsResolverAddressTypes {
    /**
     * Only resolve IPv4 addresses.
     */
    IPV4_ONLY,
    /**
     * Only resolve IPv6 addresses.
     */
    IPV6_ONLY,
    /**
     * Attempt to resolve both, prefer IPv4 addresses over IPv6 ones.
     * Failure to resolve IPv6 won't result in an error.
     */
    IPV4_PREFERRED,
    /**
     * Attempt to resolve both, prefer IPv6 addresses over IPv4 ones.
     * Failure to resolve IPv4 won't result in an error.
     */
    IPV6_PREFERRED,
    /**
     * Attempt to resolve IPv4 and IPv6 addresses, return all results.
     * Failure to resolve IPv6 won't result in an error.
     */
    IPV4_PREFERRED_RETURN_ALL,
    /**
     * Attempt to resolve IPv6 and IPv4 addresses, return all results.
     * Failure to resolve IPv4 won't result in an error.
     */
    IPV6_PREFERRED_RETURN_ALL;

    /**
     * The default value, based on "java.net" system properties: {@code java.net.preferIPv4Stack} and
     * {@code java.net.preferIPv6Stack}.
     *
     * @return the system default value.
     */
    static DnsResolverAddressTypes systemDefault() {
        return fromNettyType(DnsNameResolverBuilder.computeResolvedAddressTypes());
    }

    private static DnsResolverAddressTypes fromNettyType(final ResolvedAddressTypes resolvedAddressType) {
        switch (resolvedAddressType) {
            case IPV4_ONLY:
                return DnsResolverAddressTypes.IPV4_ONLY;
            case IPV6_ONLY:
                return DnsResolverAddressTypes.IPV6_ONLY;
            case IPV4_PREFERRED:
                return DnsResolverAddressTypes.IPV4_PREFERRED;
            case IPV6_PREFERRED:
                return DnsResolverAddressTypes.IPV6_PREFERRED;
            default:
                throw new IllegalArgumentException("Unknown value for " + ResolvedAddressTypes.class.getName() +
                        ": " + resolvedAddressType);
        }
    }

    static ResolvedAddressTypes toNettyType(final DnsResolverAddressTypes dnsResolverAddressType) {
        switch (dnsResolverAddressType) {
            case IPV4_ONLY:
                return ResolvedAddressTypes.IPV4_ONLY;
            case IPV6_ONLY:
                return ResolvedAddressTypes.IPV6_ONLY;
            case IPV4_PREFERRED:
            case IPV4_PREFERRED_RETURN_ALL:
                return ResolvedAddressTypes.IPV4_PREFERRED;
            case IPV6_PREFERRED:
            case IPV6_PREFERRED_RETURN_ALL:
                return ResolvedAddressTypes.IPV6_PREFERRED;
            default:
                throw new IllegalArgumentException("Unknown value for " + DnsResolverAddressTypes.class.getName() +
                        ": " + dnsResolverAddressType);
        }
    }
}
