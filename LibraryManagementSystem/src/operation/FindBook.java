package operation;

import book.BookList;

import java.util.Scanner;

public class FindBook implements IOperation{
    @Override
    public void work(BookList bookList) {
        System.out.println("请输入要查找的书籍：");
        Scanner scanner = new Scanner(System.in);
        String bookName = scanner.next();
        int size = bookList.getSize();
        int flag = 0;
        for (int i = 0; i < size; i++) {
            if(bookName.equals(bookList.getBooks()[i].getName())) {
                System.out.println(bookList.getBooks()[i].toString());
                flag = 1;
            }
        }
        if(flag == 0) {
            System.out.println("未找到书籍！");
        }
    }
}
