package Stack;
import Stack.StacksImpl.ListNodeStackImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListNodeStackTest {
    public static void main(String[] args) {
        //测试空栈
        ListNodeStackImpl<Integer> stack = new ListNodeStackImpl<>();
        stack.print();

        //压入8个元素
        for (int i = 0; i < 8; i++) {
            stack.push(i);
        }
        System.out.println("top = "+stack.peek());
        stack.print();

        //弹出3个元素
        for (int i = 0; i < 3; i++) {
            System.out.println(stack.pop());
        }
        System.out.println("top = "+stack.peek());
        stack.print();

    }
}
