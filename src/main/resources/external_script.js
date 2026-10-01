print("Hello, I am external resource script!");
var name = "Kumar";

//var department = execution.getVariable("Department");
//print("Department: " + department);
var  age = execution.getVariable("Age")
execution.setVariable("Name", name);
execution.setVariable("Department", "MPC");

print("Print My Age: "+ age);
if (age === null) {
  execution.setVariable("Age", "25");
}