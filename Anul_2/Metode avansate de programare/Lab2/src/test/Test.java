package test;

import container.Container;
import container.QueueContainer;
import container.StackContainer;
import container.TaskContainerFactory;
import container.Strategy;

import domain.SortingStrategy;
import domain.Task;
import domain.MessageTask;
import domain.SortingTask;

import taskRunner.TaskRunner;
import taskRunner.StrategyTaskRunner;
import taskRunner.PrinterTaskRunner;
import taskRunner.DelayTaskRunner;

import java.time.LocalDateTime;
import java.util.Arrays;


public class Test {
    public static void main(String[] args) {
        MessageTaskTest();
        SortingTaskTest.sortTest();
        QueueContainerTest.queueTest();
        StackContainerTest.stackTest();
        testFactory();
        TaskRunnerTest.runnerTest(args);
    }

    public static void MessageTaskTest() {
        MessageTask task1 = new MessageTask("1", "descriere1", "mesaj1", "from1", "to1", LocalDateTime.now());
        MessageTask task2 = new MessageTask("2", "descriere2", "mesaj2", "from2", "to2", LocalDateTime.now());
        MessageTask task3 = new MessageTask("3", "descriere3", "mesaj3", "from3", "to3", LocalDateTime.now());
        MessageTask task4 = new MessageTask("4", "descriere4", "mesaj4", "from4", "to4", LocalDateTime.now());
        MessageTask task5 = new MessageTask("5", "descriere5", "mesaj5", "from5", "to5", LocalDateTime.now());

        MessageTask[] task = new MessageTask[]{task1, task2, task3, task4, task5};

        for(MessageTask i: task)
            System.out.println(i);

        System.out.println();
    }

    private static class SortingTaskTest extends SortingTask {
        public SortingTaskTest(int[] vector, SortingStrategy strategy) {
            super(vector, strategy);
        }

        public static void sortTest() {

            int[] unsorted = {7, 3, 5, 2, 1, 6, 4};
            int[] sorted = {1, 2, 3, 4, 5, 6, 7};

            SortingTask test1 = new SortingTask(unsorted, SortingStrategy.BUBBLESORT);
            SortingTask test2 = new SortingTask(unsorted, SortingStrategy.QUICKSORT);

            assert Arrays.equals(test1.vector, sorted) : "sorting problems";
            assert Arrays.equals(test1.vector, test2.vector) : "sorting problems";
        }
    }

    private static class QueueContainerTest extends QueueContainer {
        public static void queueTest() {
            QueueContainer queueContainer = new QueueContainer();
            assert (queueContainer.isEmpty());

            MessageTask task1 = new MessageTask("1", "a", "a", "a", "a", LocalDateTime.now());
            MessageTask task2 = new MessageTask("1", "a", "a", "a", "a", LocalDateTime.now());
            MessageTask task3 = new MessageTask("1", "a", "a", "a", "a", LocalDateTime.now());
            queueContainer.add(task1);
            assert !queueContainer.isEmpty() : "queue is empty";
            assert queueContainer.getTasks().get(0).equals(task1) : "queue equal element";

            queueContainer.add(task2);
            queueContainer.add(task3);
            assert queueContainer.size() == 3 : "queue adding";

            assert(queueContainer.remove()==task1);
            assert queueContainer.size() == 2 : "queue removing";

            assert(queueContainer.remove()==task2);
            assert queueContainer.size() == 1 : "queue removing";

            queueContainer.remove();
            assert queueContainer.isEmpty() : "queue is empty after removing";

            assert queueContainer.remove() == null : "removing from an empty queue";
            assert queueContainer.isEmpty() : "queue is empty";
        }
    }

    private static class StackContainerTest extends StackContainer {
        public static void stackTest() {
            StackContainer stackContainer = new StackContainer();
            assert (stackContainer.isEmpty());

            MessageTask task1 = new MessageTask("1", "1", "1", "1", "1", LocalDateTime.now());
            stackContainer.add(task1);
            assert !stackContainer.isEmpty() : "stack is empty";

            MessageTask task2 = new MessageTask("2", "2", "2", "2", "1", LocalDateTime.now());
            stackContainer.add(task2);
            assert stackContainer.size() == 2 : "stack adding";


            Task rmv = stackContainer.remove();
            assert rmv.equals(task2) : "stack equals";
            assert stackContainer.size() == 1 : "stack size";

            rmv = stackContainer.remove();
            assert rmv.equals(task1) : "stack equals";
            assert stackContainer.isEmpty() : "stack is empty";

            assert stackContainer.remove() == null : "removing from an empty stack";
            assert (stackContainer.isEmpty());
        }
    }
    private static void testFactory() {
        TaskContainerFactory taskContainerFactory = TaskContainerFactory.getInstance();
        Container queue = taskContainerFactory.createContainer(Strategy.FIFO);
        assert queue.getClass() == QueueContainer.class;

        Container stack = taskContainerFactory.createContainer(Strategy.LIFO);
        assert stack.getClass() == StackContainer.class;
    }

    private static class TaskRunnerTest {
        public static void runnerTest(String[] args) {
            testStack();
            testQueue();
            testPrinter(args);
            testDelay(args);
        }

        private static void testStack() {
            System.out.println("Stack:");
            StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf("LIFO"));

            testRun(strategyTaskRunner);
        }

        private static void testQueue() {
            System.out.println("Queue:");
            StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf("FIFO"));

            testRun(strategyTaskRunner);
        }

        private static void testRun(TaskRunner strategyTaskRunner) {
            MessageTask task1 = new MessageTask("1", "mesaj1", "abc", "xyz", "abb", LocalDateTime.now());
            MessageTask task2 = new MessageTask("2", "mesaj2", "def", "abc", "bcb", LocalDateTime.now());
            MessageTask task3 = new MessageTask("3", "mesaj3", "zyx", "xyz", "yyy", LocalDateTime.now());

            strategyTaskRunner.addTask(task1);
            System.out.println("Added 1");
            strategyTaskRunner.addTask(task2);
            System.out.println("Added 2");
            strategyTaskRunner.addTask(task3);
            System.out.println("Added 3");

            strategyTaskRunner.executeAll();
            System.out.println();
        }

        private static void testPrinter(String[] args) {
            System.out.println("Printer:");
            TaskRunner printer = new PrinterTaskRunner(new StrategyTaskRunner(Strategy.valueOf(args[0])));
            testRun(printer);
        }

        private static void testDelay(String[] args) {
            System.out.println("Delay:");
            TaskRunner delay = new DelayTaskRunner(new StrategyTaskRunner(Strategy.valueOf(args[0])));
            testRun(delay);
        }
    }
}
