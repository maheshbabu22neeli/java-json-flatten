# java-json-flatten
This Service will flatten and unFlatten the Json Values




#### Flatten Json
```

Example Output:
-----------------
{id="CUS001", name="Neeli", phone[0].number="0469001001", phone[0].type="Office", phone[0].state="InActive", 
phone[1].number="0469001002", phone[1].type="Home", phone[1].state="Active", phone[2].number="0469001003", 
phone[2].type="Mobile", phone[2].state="Active", address[0].unit="Unit 100", address[0].street="Street 100", 
address[0].country="Country 100", address[0].type="permanent", address[1].unit="Unit 101", 
address[1].street="Street 101", address[1].country="Country 101", address[1].type="current", address[2].unit="Unit 102",
address[2].street="Street 102", address[2].country="Country 102", address[2].type="office"}

```