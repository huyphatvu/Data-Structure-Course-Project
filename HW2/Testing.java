/**
 * Created by Huy Vu on 9/22/2017.
 */
public class Testing {
    public static void main(String[] args){
        SinglyLinkedList<Integer> list;
        list = new SinglyLinkedList<>();
        SinglyLinkedList<Integer>.SinglyLinkedListIterator it = list.iterator();
         for(int i = 9; i > 0; i--){
            System.out.println("adding "+i);
          it.add(new Integer(i));
            it.next();
         }

        list.remove(9);
        list.remove(7);
        list.remove(5);
        list.remove(3);
        list.remove(1);

        it = list.iterator();
        int evenNum = 8;
        while(it.hasNext()){
            System.out.println(evenNum + "    " +it.next().intValue());
            evenNum = evenNum - 2;
        }
    }
}
