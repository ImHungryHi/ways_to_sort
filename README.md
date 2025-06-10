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