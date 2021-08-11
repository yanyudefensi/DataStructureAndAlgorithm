package FirstPart;

public class onePointSix {
    //普通的二分搜索
    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if(nums[mid] < target) {
                left = mid + 1;
            }  else if(nums[mid] > target) {
                right = mid - 1;
            } else if(nums[mid] == target) {
                return mid;
            }
        }
        return -1;
    }
    //搜索左侧边界的二分搜索
    public int leftBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] < target) {
                //搜索区间变为【mid+1.right】
                left = mid + 1;
            } else if(nums[mid] > target) {
                right = mid - 1;
            } else if(nums[mid] == target) {
                //收缩右侧边界
                right = mid - 1;
            }
        }
        //检查出界情况
        if(left >= nums.length || nums[right] != target) {
            return -1;
        }
        return left;
    }
    //搜索右侧边界的二分搜索
    public int rightBound(int[] nums, int target) {
        int right = 0, left = nums.length - 1;
        while(right <= left) {
            int mid = left + (right - left) / 2;
            if(nums[mid] < target) {
                left = mid + 1;
            } else if(nums[mid] > target) {
                right = mid - 1;
            } else if(nums[mid] == target) {
                //收缩左侧边界
                left = mid + 1;
            }
        }
        // 当 right = left - 1，有出界可能
        if(right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }

}
