package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "__typename"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegistryCommand.class, name = "RegistryCommand"),
        @JsonSubTypes.Type(value = RegistryCompanyCommand.class, name = "RegistryCompanyCommand")
})
@JsonTypeName("ICommand")
public interface ICommand extends Serializable {
    void execute();

    void undo();

    Type GetType();

    void SetType(ICommand.Type type);

    String getDescription();

    void setDescription(String description);

    class Type {
        public Type(boolean approvable, boolean saveable) {
            this.approvable = approvable;
            this.saveable = saveable;
        }

        public Type() {
            this.approvable = false;
            this.saveable = false;
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
