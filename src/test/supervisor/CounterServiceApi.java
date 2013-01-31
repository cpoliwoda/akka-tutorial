/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.supervisor;

    public interface CounterServiceApi {

        public static final Object GetCurrentCount = "GetCurrentCount";

        public static class CurrentCount {

            public final String key;
            public final long count;

            public CurrentCount(String key, long count) {
                this.key = key;
                this.count = count;
            }

            public String toString() {
                return String.format("%s(%s, %s)", getClass().getSimpleName(), key, count);
            }
        }

        public static class Increment {

            public final long n;

            public Increment(long n) {
                this.n = n;
            }

            public String toString() {
                return String.format("%s(%s)", getClass().getSimpleName(), n);
            }
        }

        public static class ServiceUnavailable extends RuntimeException {

            public ServiceUnavailable(String msg) {
                super(msg);
            }
        }
    }//CounterServiceApi