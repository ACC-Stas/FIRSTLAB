package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.Id;
import main.banksystem.entities.SalaryProject;

import java.util.Map;

@JsonTypeName("BuildSalaryProjectCommand")
public class BuildSalaryProjectCommand implements ICommand{
    private ICommand.Type type;
    private SalaryProject salaryProject;
    private String description;

    @Override
    public ICommand.Type getType() {
        return type;
    }

    public SalaryProject getSalaryProject() {
        return salaryProject;
    }

    public void setSalaryProject(SalaryProject salaryProject) {
        this.salaryProject = salaryProject;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public BuildSalaryProjectCommand(Id userId, SalaryProject salaryProject, ICommand.Type type) {
        this.salaryProject = salaryProject;
        this.type = type;
        this.description = String.format("User %d want to create salary project %s", userId.getId(), salaryProject.toString());
    }

    @JsonCreator
    public BuildSalaryProjectCommand() {
        this.salaryProject = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> credits = dataBase.downloadMap(DataBase.CREDIT_PART, SalaryProject.class);

        if (credits.containsKey(salaryProject.getSalaryProjectId())) {
            return;
        }

        dataBase.save(salaryProject.getSalaryProjectId(), DataBase.SALARY_PART, salaryProject);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(salaryProject.getSalaryProjectId(), DataBase.SALARY_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

}
