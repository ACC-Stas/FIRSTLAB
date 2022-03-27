package main.banksystem;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.User;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CPU {
    public CPU(User cpuUser) {
        dataBase = DataBase.getInstance();
        this.cpuUser = cpuUser;
    }

    private User cpuUser;
    private final DataBase dataBase;
    private static final StringConverter<ICommand> commandConverter = new StringConverter<>();
    private static final StringConverter<Queue<String>> queueConverter = new StringConverter<>();
    private static final StringConverter<Stack<String>> stackConverter = new StringConverter<>();

    public User getCpuUser() {
        return cpuUser;
    }

    public void setCpuUser(User cpuUser) {
        this.cpuUser = cpuUser;
    }

    public void heldCommand(ICommand command) {
        if (command.getType().isApprovable()) {
            String rawData = dataBase.download(cpuUser.getIdx(), DataBase.QUEUE_PART);
            Queue<String> stringQueue = queueConverter.deserialize(rawData, Queue.class);
            Queue<ICommand> queue = commandConverter.deserialize(stringQueue, ICommand.class);

            if (queue == null) {
                queue = new LinkedList<>();
            }
            queue.add(command);

            stringQueue = commandConverter.serialize(queue);
            rawData = queueConverter.serialize(stringQueue);
            dataBase.save(cpuUser.getIdx(), DataBase.QUEUE_PART, rawData);
            return;
        }

        command.execute();
        if (command.getType().isSaveable()) {
            String rawData = dataBase.download(cpuUser.getIdx(), DataBase.STACK_PART);
            Stack<String> stringStack = stackConverter.deserialize(rawData, Stack.class);
            Stack<ICommand> stack = commandConverter.deserialize(stringStack, ICommand.class);

            if (stack == null) {
                stack = new Stack<>();
            }
            stack.push(command);

            stringStack = commandConverter.serialize(stack);
            rawData = stackConverter.serialize(stringStack);
            dataBase.save(cpuUser.getIdx(), DataBase.STACK_PART, rawData);
        }
    }
}
