package com.github.thumannw.roots.projection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HyperVectFactory {

    private static class Key {
        private double[] arr;

        @Override
        public int hashCode() {
            // performance is higher without hashing
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Key other = (Key) obj;
            return Arrays.equals(this.arr, other.arr);
        }
    }

    private class Cached extends HypersphericalVector {
        public Cached(double... angles) {
            super(angles);
        }

        @Override
        protected double[] coordinatesFromAngles(double[] angles) {
            Optional<double[]> fromCache = this.getFromCache(angles);
            if (fromCache.isPresent()) {
                return fromCache.get();
            }
            double[] result = super.coordinatesFromAngles(angles);
            this.putIntoCache(angles, result);
            return result;
        }

        private Optional<double[]> getFromCache(double[] angles) {
            Key key = new Key();
            key.arr = angles;
            double[] val = HyperVectFactory.this.cacheMap.get(key);
            return Optional.ofNullable(val);
        }

        private void putIntoCache(double[] angles, double[] coordinates) {
            Key key = new Key();
            key.arr = angles;
            HyperVectFactory.this.cacheMap.put(key, coordinates);
        }
    }

    private final Map<Key, double[]> cacheMap = new HashMap<>();

    public HypersphericalVector create(double[] angles) {
        return new Cached(angles);
    }

}
