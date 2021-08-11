package ThirdPart;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class threePointTwo {

  public class LFUCache {
    // 构造容量为capacity的缓存
    int cap;
    // 记录最小的频次
    int minFreq;
    // keytoval, KV表
    HashMap<Integer, Integer> keyToVal;
    // keytofreq, KF表
    HashMap<Integer, Integer> keyToFreq;
    // freqtokey, FK表
    HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;

    public int get(int key) {
      if (!keyToVal.containsKey(key)) {
        return -1;
      }
      // 增加key 对应的 freq
      increaseFreq(key);
      return keyToVal.get(key);
    }

    public void put(int key, int value) {
      if (this.cap <= 0) return;
      if (keyToVal.containsKey(key)) {
        keyToVal.put(key, value);
        increaseFreq(key);
        return;
      }
      if (this.cap <= keyToVal.size()) {
        removeMinFreqKey();
      }
      keyToVal.put(key, value);
      keyToFreq.put(key, 1);
      freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
      freqToKeys.get(1).add(key);
      this.minFreq = 1;
    }

    // LFU的核心逻辑
    private void removeMinFreqKey() {
      // freq 最小的key列表
      LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
      // 其中最先被插入的那个key就应该是被淘汰的key
      int deletedKey = keyList.iterator().next();
      // 更新FK表,从这里开始更新三个表
      keyList.remove(deletedKey);
      if (keyList.isEmpty()) {
        freqToKeys.remove(this.minFreq);
        // 不需要更新minFreq, 因为minFreq只有在put上才使用,put上就插入新的key，并重置minFreq
      }
      keyToVal.remove(deletedKey);
      keyToFreq.remove(deletedKey);
    }

    private void increaseFreq(int key) {
      int freq = keyToFreq.get(key);
      keyToFreq.put(key, freq + 1);
      freqToKeys.get(freq).remove(key);
      freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
      freqToKeys.get(freq + 1).add(key);

      if (freqToKeys.get(freq).isEmpty()) {
        freqToKeys.remove(freq);
        // 如果这个freq正好是minFreq，更新minFreq
        if (freq == this.minFreq) {
          this.minFreq++;
        }
      }
    }
  }
}
