import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public boolean makeSquare(int[] nums) {
        int sum = IntStream.of(nums).sum();
        if (sum%4 != 0) {
            return false;
        } else {
            if (IntStream.of(nums).max().getAsInt() > sum/4) {
                return false;
            } else {
                List<Integer> numbers = IntStream.of(nums).mapToObj(i->i).sorted().collect(Collectors.toList());
                List<Integer> visit = new ArrayList<>();
                numbers.forEach(i->visit.add(0));
                Stack<Pair> qu = new Stack<>();
                qu.push(new Pair(new ArrayList<Integer>(), visit));
                while (!qu.isEmpty()) {
                    Pair curr = qu.pop();
                    List<List<Integer>> next = findAllGroupsSumTo(numbers, new ArrayList<>(curr.visit), sum/4);
                    if (next != null) {
                        for (int i = 0; i < next.size(); i++) {
                            List<Integer> next_visit = new ArrayList<>(curr.visit);
                            next.get(i).forEach(n->next_visit.set(n, 1));
                            if (next_visit.stream().allMatch(j->j==1))
                                return true;
                            else
                                qu.push(new Pair(next.get(i), next_visit));
                        }
                    }
                }
                return false;
            }
        }
    }

    class Pair {
        public List<Integer> indx;
        public List<Integer> visit;

        public Pair(List<Integer> indx, List<Integer> visit) {
            this.indx = indx;
            this.visit = visit;
        }
    }

    private List<List<Integer>> findAllGroupsSumTo(List<Integer> nums, List<Integer> visit, int sum) {
        if (sum <= 0)
            return null;
        else {
            List<List<Integer>> result = new ArrayList<>();
            for (int i = nums.size()-1; i >= 0; i--) {
                if (visit.get(i) == 0) {
                    visit.set(i, 1);
                    if (nums.get(i) == sum) {
                        List<Integer> curr = new ArrayList<>();
                        curr.add(i);
                        result.add(curr);
                    }
                    else if (nums.get(i) < sum) {
                        List<List<Integer>> next = findAllGroupsSumTo(nums, new ArrayList<>(visit), sum-nums.get(i));
                        if (next != null) {
                            for (int j = 0; j < next.size(); j++) {
                                next.get(j).add(i);
                                result.add(next.get(j));
                            }
                        }
                    }
                }
            }
            if (result.size() == 0)
                return null;
            else
                return result;
        }
    }

    public static void main(String[] args) {
//        int[] nums = {1,1,2,2,2};
//        int[] nums = {3,3,3,3,4};
        int[] nums = {3,3,2,2,2,2,2,2,2,2,2,2,2,2,2};

        Solution solution = new Solution();
        System.out.println(solution.makeSquare(nums));
    }
}
