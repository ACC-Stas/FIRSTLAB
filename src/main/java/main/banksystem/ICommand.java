package main.banksystem;

public interface ICommand {
    public void execute();

    public void undo();

    public Type GetType();

    public void SetType();

    public static class Type {
        private boolean approvable;
        private boolean saveable;

        public boolean isSaveable() {
            return saveable;
        }

        public void setSaveable(boolean saveable) {
            this.saveable = saveable;
        }

        public boolean isApprovable() {
            return approvable;
        }

        public void setApprovable(boolean approvable) {
            this.approvable = approvable;
        }
    }
}
