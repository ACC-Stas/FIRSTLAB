package main.banksystem.containers;

import main.banksystem.commands.TransferCommand;

public class SalaryProject {
    private Id salaryProjectId;
    private Id billFromId;
    private Id billToId;
    private double sum;

    public SalaryProject() {
        billFromId = null;
        billToId = null;
        sum = -5;
        salaryProjectId = null;
    }

    public SalaryProject(Id billFromId, Id billToId, double sum, Id salaryProjectId) {
        this.billFromId = billFromId;
        this.billToId = billToId;
        this.sum = sum;
        this.salaryProjectId = salaryProjectId;
    }

    public Id getBillFromId() {
        return billFromId;
    }

    public void setBillFromId(Id billFromId) {
        this.billFromId = billFromId;
    }

    public Id getBillToId() {
        return billToId;
    }

    public void setBillToId(Id billToId) {
        this.billToId = billToId;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Id getSalaryProjectId() {
        return salaryProjectId;
    }

    public void setSalaryProjectId(Id salaryProjectId) {
        this.salaryProjectId = salaryProjectId;
    }

    public TransferCommand buildTransferCommand() {
        Transfer transfer = new Transfer();
        transfer.setBillFromId(this.billFromId);
        transfer.setBillToId(this.billToId);
        transfer.setValue(this.sum);

        return new TransferCommand(transfer);
    }
}
