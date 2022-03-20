package main.banksystem;

import java.io.Serializable;

public interface ICommand extends Serializable {
    public void execute();

    public void undo();

    public Type GetType();

    public void SetType(ICommand.Type type);

    public static class Type {
        public Type(boolean approvable, boolean saveable) {
            this.approvable = approvable;
            this.saveable = saveable;
        }

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
