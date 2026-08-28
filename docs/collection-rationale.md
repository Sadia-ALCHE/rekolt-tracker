# Collections Rationale (Objective 3)

## List<Delivery> deliveries - ArrayList
I chose an ArrayList to store the deliveries because these deliveries are added one at a time,
usually at the end of the list. We also go through the whole list when we 
need to calculate or display the season figures. I believe an ArrayList works 
well for both of these operations.

## double[][] weeklyGrid - 2D array
I used a 2D array because the weekly grid has a fixed size of 20 weeks and 4
produce codes. Since the number of rows and columns is already known and doesn't
change, an array is simpler to use in this case. I'm able to access any value
directly using `[week][column]`.

## Map<String, Double> paymentPerMember - HashMap
I used a HashMap to keep track of how much each member should be paid. The
member ID is used as the key, which makes it easy for me to find and update a member's
total whenever a new delivery is recorded. Also, since I also don't need the members
to be stored in any particular order, so HashMap was the best.

## Map<String, List<Delivery>> deliveriesPerMember - map of lists
I used a map of lists to keep track of the deliveries made by each member. Each
member ID points to a list containing that member's deliveries. This makes
it easier to generate the season report because the deliveries are already
grouped by member instead of having to search through the entire delivery list.

## Set<String> memberIds - HashSet
I used a HashSet to keep track of the different member IDs that appear during the
season. A set is useful in this case because we only want each member ID to
appear once and since the HashSet already handles duplicates, so it is simpler.

## Sorting - Comparable and Comparator
Delivery implements Comparable because we have one main or natural way of 
comparing deliveries, which is, by their net payable value. This is useful when we 
need to find the top five deliveries. A Comparator would be useful if I needed another way to sort the objects.I could create
a different comparator without changing the natural ordering of Delivery.

## Search - findDeliveryById
The findDeliveryById method returns an Optional<Delivery> instead of returning
null. This makes it clear that a delivery might not be found and means the 
code using the method has to handle that situation.

## Removal - Iterator
deliveriesWithRejectedRemoved() uses an Iterator because we need to remove
items while going through the list. Removing an item directly from a list inside
a normal for-each loop can cause a ConcurrentModificationException. The Iterator
provides a safe way to remove the current item while continuing through the list.
