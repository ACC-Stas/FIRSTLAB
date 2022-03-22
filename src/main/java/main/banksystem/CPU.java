package main.banksystem;

import main.banksystem.commands.ICommand;
import main.banksystem.containers.User;

import java.util.Queue;
import java.util.Stack;

public class CPU {
    CPU(User cpuUser) {
        dataBase = DataBase.GetInstance();
        queueConverter = new StringConverter<>();
        stackConverter = new StringConverter<>();
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
    private final StringConverter<Queue<ICommand>> queueConverter;
    private final StringConverter<Stack<ICommand>> stackConverter;

    public void HeldCommand(ICommand command) {
        if (command.GetType().isApprovable()) {
            Queue<ICommand> queue = queueConverter.Deserialize(dataBase.Download(cpuUser.getIdx(), DataBase.QUEUE_PART));
            queue.add(command);
            dataBase.Save(cpuUser.getIdx(), DataBase.QUEUE_PART, queueConverter.Serialize(queue));
            return;
        }

        command.execute();
        if (command.GetType().isSaveable()) {
            Stack<ICommand> stack = stackConverter.Deserialize(dataBase.Download(cpuUser.getIdx(), DataBase.STACK_PART));
            stack.add(command);
            dataBase.Save(cpuUser.getIdx(), DataBase.STACK_PART, stackConverter.Serialize(stack));
        }
    }
}
