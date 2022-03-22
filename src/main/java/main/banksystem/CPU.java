package main.banksystem;
import main.banksystem.commands.ICommand;
import main.banksystem.containers.User;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CPU {
    public CPU(User cpuUser) {
        dataBase = DataBase.GetInstance();
        queueConverter = new StringConverter<>();
        stackConverter = new StringConverter<>();
        commandConverter = new StringConverter<>();
        this.cpuUser = cpuUser;
    }
    public User getCpuUser() {
        return cpuUser;
    }

    public void setCpuUser(User cpuUser) {
        this.cpuUser = cpuUser;
    }

    private User cpuUser;
    private final DataBase dataBase;
    private final StringConverter<ICommand> commandConverter;
    private final StringConverter<Queue<String>> queueConverter;
    private final StringConverter<Stack<String>> stackConverter;

    public void HeldCommand(ICommand command) {
        if (command.GetType().isApprovable()) {
            String rawData = dataBase.Download(cpuUser.getIdx(), DataBase.QUEUE_PART);
            Queue<String> stringQueue = queueConverter.Deserialize(rawData, Queue.class);
            Queue<ICommand> queue = commandConverter.Deserialize(stringQueue, ICommand.class);

            if (queue == null) {
                queue = new LinkedList<>();
            }
            queue.add(command);

            stringQueue = commandConverter.Serialize(queue);
            rawData = queueConverter.Serialize(stringQueue);
            dataBase.Save(cpuUser.getIdx(), DataBase.QUEUE_PART, rawData);
            return;
        }

        command.execute();
        if (command.GetType().isSaveable()) {
            String rawData = dataBase.Download(cpuUser.getIdx(), DataBase.STACK_PART);
            Stack<String> stringStack = stackConverter.Deserialize(rawData, Stack.class);
            Stack<ICommand> stack = commandConverter.Deserialize(stringStack, ICommand.class);

            if (stack == null) {
                stack = new Stack<>();
            }
            stack.push(command);

            stringStack = commandConverter.Serialize(stack);
            rawData = stackConverter.Serialize(stringStack);
            dataBase.Save(cpuUser.getIdx(), DataBase.STACK_PART, rawData);
        }
    }
}
