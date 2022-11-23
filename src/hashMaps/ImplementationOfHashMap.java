package hashMaps;
import java.util.*;

public class ImplementationOfHashMap {
    @SuppressWarnings("unchecked")
    public static class HashMap<K, V> {
        private class Node {
            K Key;
            V Value;

            Node(K Key, V Value) {
                this.Key = Key;
                this.Value = Value;
            }
        }

        private int size;
        private LinkedList<Node>[] bucket;

        // n-> size -> no of key-value pairs
        // N-> bucket.length -> no of buckets(array indices)
        // Lamda-> n/N load factor
        // initial capacity of buckets is generally 16(has to be a power of 2)
        // Load factor -> 0.75
        public HashMap() {
            this.size = 0;
            this.bucket = new LinkedList[16];
            for (int i = 0; i < bucket.length; i++) {
                this.bucket[i] = new LinkedList<>();
            }
        }

        private int hashFunction(K Key) {
            int hc = Key.hashCode();
            return Math.abs(hc) % bucket.length;
        }

        public void put(K Key, V Value) {
            int bucket_index = hashFunction(Key);
            int data_index = getIndexWithInBucket(Key, bucket_index);
            if (data_index != -1) {
                Node node = bucket[bucket_index].get(data_index);
                node.Value = Value;
            } else {
                Node node = new Node(Key, Value);
                bucket[bucket_index].add(node);
                size++;
            }
            // load factor = (no of key-value pair / bucket size)
            double load_factor = (size * 1.0) / bucket.length;
            if (load_factor > 0.75) {
                reHash();
            }
        }

        private int getIndexWithInBucket(K key, int bucket_index) {
            int di = 0;
            for (Node node : bucket[bucket_index]) {
                if (node.Key.equals(key) == true) {
                    return di;
                }
                di++;
            }
            return -1;
        }

        private void reHash() {
            LinkedList<Node>[] oll = bucket;
            bucket = new LinkedList[oll.length << 1];
            for (int i = 0; i < bucket.length; i++) {
                this.bucket[i] = new LinkedList<>();
            }
            size = 0;
            for (int i = 0; i < oll.length; i++) {
                for (Node node : oll[i]) {
                    put(node.Key, node.Value);
                }
            }
        }

        public void display() {
            System.out.println("Display Begins");
            for (int bi = 0; bi < bucket.length; bi++) {
                System.out.print("Bucket" + bi + " ");
                for (Node node : bucket[bi]) {
                    System.out.print(node.Key + "@" + node.Value + " ");
                }
                System.out.println(".");
            }
            System.out.println("Display Ends");
        }

        public V get(K Key) {
            int bucket_index = hashFunction(Key);
            int data_index = getIndexWithInBucket(Key, bucket_index);
            if (data_index != -1) {
                Node node = bucket[bucket_index].get(data_index);
                return node.Value;
            } else {
                return null;
            }

        }

        public V remove(K Key) {
            int bucket_index = hashFunction(Key);
            int data_index = getIndexWithInBucket(Key, bucket_index);
            if (data_index != -1) {
                Node node = bucket[bucket_index].remove(data_index);
                size--;
                return node.Value;
            } else {
                return null;
            }
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K Key) {
            int bucket_index = hashFunction(Key);
            int data_index = getIndexWithInBucket(Key, bucket_index);
            if (data_index != -1) {
                return true;
            } else {
                return false;
            }
        }

        public ArrayList<K> keySet() {
            ArrayList<K> key = new ArrayList<>();
            for (int i = 0; i < bucket.length; i++) {
                for (Node node : bucket[i]) {
                    key.add(node.Key);
                }
            }
            return key;
        }

    }

    public static void main(String[] args) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        hm.put(1, 19);
        hm.put(2, 20);
        hm.put(3, 7);
        System.out.println(hm.containsKey(99));
        for (int key : hm.keySet()) {
            System.out.println(key);
        }

    }

}
