package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;
import main.banksystem.entities.SalaryProject;

import java.util.ArrayList;

@JsonTypeName("PayEmployeesCommand")
public class PayEmployeesCommand implements ICommand {
    private Id specialistId;
    private Id companyId;
    private ICommand.Type type;
    private String description;
    private ArrayList<TransferCommand> commands;

    public PayEmployeesCommand(Id specialistId, Id companyId, ICommand.Type type) {
        this.companyId = companyId;
        this.specialistId = specialistId;
        this.type = type;
        this.description = String.format("Specialist %d want to pay to company employees", specialistId.getId());
        this.commands = new ArrayList<>();
    }

    @JsonCreator
    public PayEmployeesCommand() {
        this.companyId = null;
        this.specialistId = null;
        this.type = null;
        this.description = String.format("No description");
        this.commands = new ArrayList<>();
    }

    public Id getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Id companyId) {
        this.companyId = companyId;
    }

    public Id getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Id specialistId) {
        this.specialistId = specialistId;
    }

    @Override
    public ICommand.Type getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Company company = dataBase.download(companyId, DataBase.COMPANY_PART, Company.class);
        if (company == null) {
            description = String.format("No company with PAN %d", companyId.getId());
            return;
        }
        commands.clear();
        for (Id salaryProjectId : company.getSalaryProjectIds()) {
            SalaryProject salaryProject = dataBase.download(salaryProjectId, DataBase.SALARY_PART, SalaryProject.class);
            TransferCommand command = salaryProject.buildTransferCommand();
            commands.add(command);
        }

        for (TransferCommand command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (TransferCommand command : commands) {
            command.undo();
        }
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public ArrayList<TransferCommand> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<TransferCommand> commands) {
        this.commands = commands;
    }

    public String toString() {
        return this.description;
    }
}
