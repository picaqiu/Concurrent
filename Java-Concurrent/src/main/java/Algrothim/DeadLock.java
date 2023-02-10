package Algrothim;

import java.util.ArrayList;
import java.util.List;

public class DeadLock {

    public class Account {
        private int id;
        private int balance;
        // actr应该为单例
        private final Allocator actr = new Allocator();

        /**
         * 存在互相转账而产生死锁的情况
         *
         * @param target
         * @param amount
         */
        public void transfer1(Account target, int amount) {
            synchronized (this) {
                synchronized (target) {
                    if (this.balance > amount) {
                        this.balance -= amount;
                        target.balance += amount;
                    }
                }
            }
        }

        /**
         * @param target
         * @param amount 按账户的id进行排序，转账时，始终先锁住id小的，再锁住id大的，这样就避免了循环依赖的情况
         */
        public void transfer2(Account target, int amount) {
            Account left = this;
            Account right = target;
            if (this.id > target.id) {
                left = target;
                right = this;
            }
            synchronized (left) {
                synchronized (right) {
                    if (this.balance > amount) {
                        this.balance -= amount;
                        target.balance += amount;
                    }
                }
            }
        }


        /**
         * 破坏占有且等待
         * @param target
         * @param amount
         */
        public void transfer3(Account target, int amount) {
            // 一次性申请转出账户和转入账户，直到成功
            while (!actr.apply(this, target))
                try {
                    // 锁定转出账户
                    synchronized (this) {
                        // 锁定转入账户
                        synchronized (target) {
                            if (this.balance > amount) {
                                this.balance -= amount;
                                target.balance += amount;
                            }
                        }
                    }
                } finally {
                    actr.free(this, target);
                }
        }
    }

    /**
     * 资源协调者，一次申请需要的所有资源，避免死锁
     */
    class Allocator {
        private List<Object> als = new ArrayList<>();

        // 一次性申请所有资源
        synchronized boolean apply(Object from, Object to) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }  // 归还资源

        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
        }
    }
}
