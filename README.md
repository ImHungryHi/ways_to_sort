# Collection of ways to sort
============================

## External merge sort

1. Split large file into sizeable blocks - eg 3 blocks of 4 lines
   - 4 8 2 6
   - 7 10 5 3
   - 9 1
2. Sort each block internally
   - 2 4 6 8
   - 3 5 7 10
   - 1 9
3. Line up the first of each block to take the lowest first
   - (2) (3) (1)
   - Result: (1)
   - New lineup for next iteration: (2) (3) (9)
     - 2 4 6 8
     - 3 5 7 10
     - 9
   - Second result: (1 2)
   - New lineup for next iteration: (4) (3) (9)
     - 4 6 8
     - 3 5 7 10
     - 9
4. Rinse and repeat. Each time a block is emptied, you continue with one less block to compare.
   - The end result will be, as expected, (1 2 3 4 5 6 7 8 9 10)

## As yet unnamed compare. Let's say DualCompare as reference to initial "differ" project code.

Using HashMap<String, Integer> to represent a list of each precise string match in 2 files.
For each line in the origin file, add 1 to the Integer count.
For each line in the target file, deduct 1 from the Integer count.
When the count is 0, we can remove as the result indicates the "same" situation.
Example:

### Origin file
`
Twas brillig and the slithy toves did gyre and gimble in the wabe.
All mimsy were the borogoves and the mome raths outgrabe.
`

### Target file
`
Twas brillig and the slithy toves did gyre and gimble in the wabe.
Oops
`

### HashMap contents
`
"All mimsy were the borogoves and the mome raths outgrabe." => 1,
"Oops" => -1
`

### Result
`
[ORIGIN +1] [All mimsy were the borogoves and the mome raths outgrabe.]
[TARGET +1] [Oops]
`

## Hybrid sort

This way of sorting uses a combination of insertion sort and merge sort.

### Insertion sort

1. Loop through each element of the initial list.
    - Eg { 5, 4, 1, 3, 2, 6, 23, 22, 0, 21, 19, 22 }
2. Compare at least 2 elements. If the first is less than the second - insert into a new list as is. 
If it's the reverse, then insert in a sorted order.
    - Eg { 5, 4 } -> {{ 4, 5 }}
    - Eg { 4, 5 } -> {{ 4, 5 }}
3. Move to the next element. If this is less than the largest, start a new list. If it's greater or equals, 
insert into the existing list.
    - Eg 1 (cref bulletin 1) -> {{ 4, 5 } { 1 }}
    - Eg 6 -> {{ 4, 5, 6 }}
4. Rinse and repeat until all elements are separated into sorted sublists.
    - {{ 4, 5 }, { 1, 3 }, { 2, 6, 23 }, { 0, 22 }, { 19, 21, 22 }}

### Merge sort

1. Using the above list of sublists, fetch the first element of each sublist to find the lowest value to insert 
into a result list.
    - { 4, 1, 2, 0, 19 } -> { 0 }
    - The remaining elements are {{ 5 }, { 3 }, { 6, 23 }, { 22 }, { 21, 22 }}
2. Remove the lowest element from the equation and replace with the next element from the same sublist that contained it.
    - { 4, 1, 2, 22, 19 }
    - The list of sublists is updated to {{ 5 }, { 3 }, { 6, 23 }, { 21, 22 }} 
          -> one less sublist to check next as it was emptied out.
3. Rinse and repeat
    - { 4, 1, 2, 22, 19 } -> { 0, 1 } || Taking 3 from second sublist {{ 5 }, { 6, 23 }, { 21, 22 }}
    - { 4, 3, 2, 22, 19 } -> { 0, 1, 2 } || Taking 6 from third sublist {{ 5 }, { 23 }, { 21, 22 }}
    - { 4, 3, 6, 22, 19 } -> { 0, 1, 2, 3 } || Sublist that contained 3 already empty {{ 5 }, { 23 }, { 21, 22 }}
    - { 4, 6, 22, 19 } -> { 0, 1, 2, 3, 4 } || Taking 5 from first sublist {{ 23 }, { 21, 22 }}
    - { 5, 6, 22, 19 } -> { 0, 1, 2, 3, 4, 5 } || Emptied first sublist {{ 23 }, { 21, 22 }}
    - { 6, 22, 19 } -> { 0, 1, 2, 3, 4, 5, 6 } || Taking 23 from third sublist {{ 21, 22 }}
    - { 23, 22, 19 } -> { 0, 1, 2, 3, 4, 5, 6, 19 } || Taking 21 from fifth sublist {{ 22 }}
    - { 23, 22, 21 } -> { 0, 1, 2, 3, 4, 5, 6, 19, 21 } || Taking 22 from fifth sublist
    - { 23, 22, 22 } -> { 0, 1, 2, 3, 4, 5, 6, 19, 21, 22 } || Emptied fourth sublist
    - { 23, 22 } -> { 0, 1, 2, 3, 4, 5, 6, 19, 21, 22, 22 } || Emptied fifth sublist
    - { 23 } -> { 0, 1, 2, 3, 4, 5, 6, 19, 21, 22, 22, 23 } || Emptied third sublist
    - All done { 0, 1, 2, 3, 4, 5, 6, 19, 21, 22, 22, 23 }