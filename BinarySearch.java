import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the sorted array elements (comma-separated): ");
        String input = scanner.nextLine();
        String[] strArray = input.split(",");
        int[] sortedArray = new int[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            sortedArray[i] = Integer.parseInt(strArray[i].trim());
        }

        System.out.print("Enter the target element to search for: ");
        int target = scanner.nextInt();
        int result = binarySearch(sortedArray, target);

        if (result == -1) {
            System.out.println("Element not found in the array.");
        } else {
            System.out.println("Element found at index " + result);
        }
    }

    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
