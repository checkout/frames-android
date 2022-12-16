package com.checkout.base.mapper

/**
 * Used to map objects
 */
public interface Mapper<FROM, TO> {
    /**
     * Map object between chosen types
     *
     * @param from - source object
     * @return target object
     */
    public fun map(from: FROM): TO
}
