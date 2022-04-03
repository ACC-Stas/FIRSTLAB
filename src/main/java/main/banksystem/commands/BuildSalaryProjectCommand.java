package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.Company;
import main.banksystem.entities.Id;
import main.banksystem.entities.SalaryProject;
import main.banksystem.entities.User;

import java.util.Map;

@JsonTypeName("BuildSalaryProjectCommand")
public class BuildSalaryProjectCommand implements ICommand{
    private Id userId;
    private Id companyId;
    private ICommand.Type type;
    private SalaryProject salaryProject;
    private String description;

    public Id getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Id companyId) {
        this.companyId = companyId;
    }

    public Id getUserId() {
        return userId;
    }

    public void setUserId(Id userId) {
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return description;
    }

    public BuildSalaryProjectCommand(Id userId, Id companyId, SalaryProject salaryProject, ICommand.Type type) {
        this.companyId = companyId;
        this.userId = userId;
        this.salaryProject = salaryProject;
        this.type = type;
        this.description = String.format("User %d want to create salary project %d", userId.getId(),
                salaryProject.getSalaryProjectId().getId());
    }

    @JsonCreator
    public BuildSalaryProjectCommand() {
        this.companyId = null;
        this.userId = null;
        this.salaryProject = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, SalaryProject> salaries = dataBase.downloadMap(DataBase.SALARY_PART, SalaryProject.class);

        if (salaries.containsKey(salaryProject.getSalaryProjectId())) {
            return;
        }

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getSalaryIds().add(this.salaryProject.getSalaryProjectId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        companies.get(companyId).getSalaryProjectIds().add(salaryProject.getSalaryProjectId());
        dataBase.save(companyId, DataBase.COMPANY_PART, companies.get(companyId));

        dataBase.save(salaryProject.getSalaryProjectId(), DataBase.SALARY_PART, salaryProject);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getSalaryIds().remove(this.salaryProject.getSalaryProjectId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        companies.get(companyId).getSalaryProjectIds().remove(salaryProject.getSalaryProjectId());
        dataBase.save(companyId, DataBase.COMPANY_PART, companies.get(companyId));

        dataBase.remove(salaryProject.getSalaryProjectId(), DataBase.SALARY_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

}
