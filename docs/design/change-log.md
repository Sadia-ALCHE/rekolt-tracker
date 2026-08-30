# Change log(Deviations from design-v1)

1. Produce has now become abstract and has three new subclasses: CerealProduce,
   PerishableProduce, CashCropProduce. In design-v1, Produce was a normal
   class with a CategoryMultiplier field. For this Objective 5, I changed it
   so that the different produce types can handle their category values differently.
   The categoryMultiplier was therefore replaced with two abstract methods,
   which each subclass implements in its own way.

2. Member now has a List<Delivery> and also implements Payable and Reportable.
   Originally, Member only had an id and name. The delivery list was added so 
   that a member can keep track of their deliveries and calculate their total.
   Payable and Reportable were also added to Member so that both interfaces
   are used by more than one class.

3. equals() and hashCode() were added to Member, Produce and Delivery. These 
   methods were not shown in design-v1, but they are needed because these objects
   are used in HashSet and HashMap in SeasonService. Each class uses its own main 
   identifier when comparing objects, like, id for Member, code for Produce, and 
   deliveryId for Delivery.
Apart from these changes, the payment calculations, grade boundaries, 
and package structure remain the same as in design-v1.