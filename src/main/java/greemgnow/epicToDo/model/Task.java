package greemgnow.epicToDo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id")
    private Long id;
    
    @Column(name = "state")
    private TaskState state = TaskState.ACTIVE;
    
    @Column(name = "name")
    private String name = "";
    
    @Column(name = "description")
    private String description = "";
    
    @Column(name = "priority")
    private Priority priority = Priority.NORMAL;
    
    @Column(name = "daily")
    private boolean daily = false;
    
    @Column(name = "comments")
    private final List<String> comments = new ArrayList<>();
    
    @Column(name = "children")
    private final List<Task> children = new ArrayList<>();
    
    @Column(name = "parent")
    private Task parent = null;
    
    
    
//Constructors
    protected Task(){
    }
    
    public Task(String name, String description){
        this.name = name;
        this.description = description;
    }

// Getters    
    public Long getId(){
        return id;
    }    
    public TaskState getState(){
        return state;
    }    
    public String getName() {
        return name;
    }    
    public String getDescription() {
        return description;
    }    
    public Priority getPriority(){
        return this.priority;
    }
    public List<String> getComments(){
        return this.comments;
    }
    public Task getParent(){
        return parent;
    }
    public List<Task> getChildren(){
        return children;
    }
    public boolean isDaily(){
        return daily;
    }
    
//Setters
    public void setState(TaskState state){
        this.state = state;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPriority(Priority priority){
        this.priority = priority;
    }
    private void setParent(Task parent){
        this.parent = parent;
    }
    public void setDaily(boolean daily){
        this.daily = daily;
    }

    
//----------------------business methods------------------------
    
//Comments methods
    public void addComment(String comment){
        comments.add(comment);
    }
    public void removeComment(int i){
        comments.remove(i);
    }
    public void changeComment(int i, String newComment){
        comments.add(i, newComment);
    }

//whith child in argument, if argument already has a parent, then it will be changed
    public void addChild(Task childTask){
        childTask.setParent(this);
        children.add(childTask);
    }
    
//method returns new Task that contains identical attributes whith sourse Task.
//parameters:
//boolean whithComments, if true - copy all comments of source Task
//boolean whithChildren, if true - recursive copy all children of source Task
    public Task takeCopy(boolean whithComments, boolean whithChildren){
        Task result = new Task();
        result.setName(name);
        result.setDescription(description);
        result.setState(state);
        result.setPriority(priority);
        result.setDaily(daily);
        parent.addChild(result);
        if(comments.size() > 0 && whithComments) {
            for(String comment : comments){
                result.addComment(comment+"");
            }
        }
        
        //need testing !!!
        if(children.size() > 0 && whithChildren){
            for(Task child : children){
                result.addChild(child.takeCopy(whithComments, whithChildren));
            }
        }
        
        return result;
    }
    
    
}
