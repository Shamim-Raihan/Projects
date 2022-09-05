import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:path/path.dart' as Path;
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/seat_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/pages/admin_login_page.dart';
import 'package:ticket_booking/pages/search_bus_list_page.dart';
import 'package:ticket_booking/provider/admin_info_provider.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:ticket_booking/provider/seat_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';

import '../model/add_new_bus_model.dart';
import '../model/admin_model.dart';

class HomePage extends StatefulWidget {
  static const String roureName = '/home_page';

  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late String from;
  late String to;
  late String busType;

  List<String> from_division = [
    'From',
    'Dhaka',
    'Rajshahi',
    'Chittagong',
    'Sylhet',
    'Barishal',
    'Mymensingh',
    'Rongpur',
    'Khulna',
  ];

  List<String> to_division = [
    'To',
    'Dhaka',
    'Rajshahi',
    'Chittagong',
    'Sylhet',
    'Barishal',
    'Mymensingh',
    'Rongpur',
    'Khulna',
  ];

  List<String> busTypeList = ['Bus Type', 'Non AC', 'AC'];

  @override
  void initState() {
    from = from_division[0];
    to = to_division[0];
    busType = busTypeList[0];
    super.initState();
  }

  String? selectedTime;

  Future<void> _selectTime(BuildContext context) async {
    final TimeOfDay? picked_s = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    );

    if (picked_s != null && picked_s != selectedTime)
      setState(() {
        selectedTime = '${picked_s.hourOfPeriod}:${picked_s.minute} ' +
            "${picked_s.period}".split('.')[1];
      });
  }

  String? selectedDate;

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(2015, 8),
        lastDate: DateTime(2101));
    if (picked != null && picked != selectedDate) {
      setState(() {
        selectedDate = DateFormat('dd/MM/yyyy').format(picked);
      });
    }
  }

  bool setLogInStatus = true;
  late BusInfoProvider provider;
  final _formKey = GlobalKey<FormState>();

  late SeatInfoProvider seatInfoProvider;
  late UserInfoProvider userInfoProvider;
  late AdminInfoProvider adminInfoProvider;

  List<BusModel> busList = [];

  @override
  void didChangeDependencies() {
    provider = Provider.of<BusInfoProvider>(context);
    seatInfoProvider = Provider.of<SeatInfoProvider>(context);
    userInfoProvider = Provider.of<UserInfoProvider>(context);
    adminInfoProvider = Provider.of<AdminInfoProvider>(context);

    busList = provider.busList;

    super.didChangeDependencies();
  }

  final cancelPhoneController = TextEditingController();
  final cancelNidController = TextEditingController();
  final searchController = TextEditingController();

  @override
  void dispose() {
    cancelPhoneController.dispose();
    cancelNidController.dispose();
    searchController.dispose();
    super.dispose();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Search Bus'),
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.green,
              ),
              child: Center(
                  child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.directions_bus,
                    size: 50,
                    color: Colors.white,
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                    'চল যাই',
                    style: TextStyle(
                        fontSize: 25,
                        fontWeight: FontWeight.bold,
                        color: Colors.white),
                  ),
                  // Text(
                  //   '',
                  //   style: TextStyle(fontSize: 14, color: Colors.white),
                  // ),
                ],
              )),
            ),
            ListTile(
              onTap: () {
                showSearchDailog();
              },
              leading: Icon(
                Icons.search,
                size: 30,
                color: Colors.black,
              ),
              title: Text(
                'Search Bus by bus name',
                style: TextStyle(fontSize: 16),
              ),
            ),
            ListTile(
              onTap: () {
                showDialog(
                    context: context,
                    builder: (context) => AlertDialog(
                          title: Text('Ticket Cancelation!!'),
                          content: Column(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              SizedBox(
                                height: 20,
                              ),
                              SizedBox(
                                height: 45,
                                child: TextField(
                                  controller: cancelPhoneController,
                                  decoration: InputDecoration(
                                    labelText: 'Enter phone number',
                                    fillColor: Colors.grey[200],
                                    filled: true,
                                    border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(20.0),
                                      borderSide: BorderSide.none,
                                    ),
                                  ),
                                ),
                              ),
                              SizedBox(
                                height: 20,
                              ),
                              SizedBox(
                                height: 45,
                                child: TextField(
                                  controller: cancelNidController,
                                  decoration: InputDecoration(
                                    labelText: 'Enter NID number',
                                    fillColor: Colors.grey[200],
                                    filled: true,
                                    border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(20.0),
                                      borderSide: BorderSide.none,
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                          actions: [
                            ElevatedButton(
                                onPressed: () {
                                  _cancelConfirmation();
                                },
                                child: Text("Confirm"),
                                style: ButtonStyle(
                                  shape: MaterialStateProperty.all<
                                          RoundedRectangleBorder>(
                                      RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(30.0),
                                  )),
                                  padding: MaterialStateProperty.resolveWith<
                                          EdgeInsetsGeometry>(
                                      (Set<MaterialState> states) {
                                    return EdgeInsets.only(
                                        left: 10,
                                        right: 10,
                                        top: 10,
                                        bottom: 10);
                                  }),
                                  backgroundColor:
                                      MaterialStateProperty.all<Color>(
                                          Colors.green),
                                )),
                          ],
                        ));
              },
              leading: Icon(
                Icons.cancel,
                size: 30,
                color: Colors.black,
              ),
              title: Text(
                'Ticket Cancelation',
                style: TextStyle(fontSize: 16),
              ),
            ),
            ListTile(
              onTap: () {
                Navigator.pushReplacementNamed(
                    context, AdminLogInPage.roureName);
              },
              leading: Icon(
                Icons.login,
                size: 30,
                color: Colors.black,
              ),
              title: Text(
                'Admin Login',
                style: TextStyle(fontSize: 16),
              ),
            ),
            ListTile(
              onTap: () {},
              leading: Icon(
                Icons.details,
                size: 30,
                color: Colors.black,
              ),
              title: Text(
                'About',
                style: TextStyle(fontSize: 16),
              ),
            ),
          ],
        ),
      ),
      body: SingleChildScrollView(
          child: Column(
        children: [
          SizedBox(
            height: 80,
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Text('From',style: TextStyle(fontSize: 20,),),
                    SizedBox(
                      height: 5,
                    ),
                    Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(20),
                      ),
                      width: MediaQuery.of(context).size.width - 40,
                      child: Padding(
                        padding: const EdgeInsets.only(left: 20, right: 20),
                        child: DropdownButton(
                          isExpanded: true,
                          underline: Container(),
                          // Initial Value
                          value: from,

                          // Down Arrow Icon
                          icon: const Icon(Icons.keyboard_arrow_down),

                          // Array list of items
                          items: from_division.map((String items) {
                            return DropdownMenuItem(
                              value: items,
                              child: new Text(items),
                            );
                          }).toList(),
                          // After selecting the desired option,it will
                          // change button value to selected value
                          onChanged: (String? newValue) {
                            setState(() {
                              from = newValue!;
                            });
                          },
                          hint: Text('From'),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Text('From',style: TextStyle(fontSize: 20,),),
                    SizedBox(
                      height: 5,
                    ),
                    Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(20),
                      ),
                      width: MediaQuery.of(context).size.width - 40,
                      child: Padding(
                        padding: const EdgeInsets.only(left: 20, right: 20),
                        child: DropdownButton(
                          isExpanded: true,
                          underline: Container(),
                          // Initial Value
                          value: to,

                          // Down Arrow Icon
                          icon: const Icon(Icons.keyboard_arrow_down),

                          // Array list of items
                          items: to_division.map((String items) {
                            return DropdownMenuItem(
                              value: items,
                              child: Text(items),
                            );
                          }).toList(),
                          // After selecting the desired option,it will
                          // change button value to selected value
                          onChanged: (String? newValue) {
                            setState(() {
                              to = newValue!;
                            });
                          },
                          hint: Text('To'),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Text('From',style: TextStyle(fontSize: 20,),),
                    SizedBox(
                      height: 5,
                    ),
                    Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(20),
                      ),
                      width: MediaQuery.of(context).size.width - 40,
                      child: Padding(
                        padding: const EdgeInsets.only(left: 20, right: 20),
                        child: DropdownButton(
                          underline: Container(),
                          isExpanded: true,
                          // Initial Value
                          value: busType,

                          // Down Arrow Icon
                          icon: const Icon(
                            Icons.keyboard_arrow_down,
                          ),

                          // Array list of items
                          items: busTypeList.map((String items) {
                            return DropdownMenuItem(
                              value: items,
                              child: new Text(items),
                            );
                          }).toList(),
                          // After selecting the desired option,it will
                          // change button value to selected value
                          onChanged: (String? newValue) {
                            setState(() {
                              busType = newValue!;
                            });
                          },
                          hint: Text('Bus Type'),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          SizedBox(
            height: 20,
          ),
          Padding(
            padding: const EdgeInsets.only(left: 20, right: 20),
            child: Container(
              height: 60,
              decoration: BoxDecoration(
                color: Colors.grey[200],
                borderRadius: BorderRadius.circular(20),
              ),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  selectedDate == null
                      ? Text(
                          'Date',
                          style: TextStyle(color: Colors.red),
                        )
                      : Text('$selectedDate'),
                  SizedBox(
                    height: 20.0,
                  ),
                  TextButton(
                    onPressed: () => _selectDate(context),
                    child: Text('Select date'),
                  ),
                ],
              ),
            ),
          ),
          SizedBox(
            height: 80,
          ),
          ElevatedButton(
              onPressed: () {
                _searchBus();
              },
              child: Text("Search Bus"),
              style: ButtonStyle(
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                )),
                padding: MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                    (Set<MaterialState> states) {
                  return EdgeInsets.only(
                      left: 130, right: 130, top: 15, bottom: 15);
                }),
                backgroundColor: MaterialStateProperty.all<Color>(Colors.green),
              )),
          SizedBox(
            height: 30,
          ),
        ],
      )),
    );
  }

  void _saveAdminInfo() async {
    final admin = AdminModel(email: 'shamim@gmail.com', password: '123456');
    await adminInfoProvider.addNewAdmin(admin);
  }

  void _searchBus() async {
    if (from == 'From') {
      final snackBar = SnackBar(
        content: const Text('Select "From" point!'),
        action: SnackBarAction(
          label: 'Ok',
          onPressed: () {
            // Some code to undo the change.
          },
        ),
      );
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      return;
    }

    if (to == 'To') {
      final snackBar = SnackBar(
        content: const Text('Select "To" point!'),
        action: SnackBarAction(
          label: 'Ok',
          onPressed: () {
            // Some code to undo the change.
          },
        ),
      );
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      return;
    }
    if (to == from) {
      final snackBar = SnackBar(
        content: const Text('Select different location!'),
        action: SnackBarAction(
          label: 'Ok',
          onPressed: () {
            // Some code to undo the change.
          },
        ),
      );
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      return;
    }

    if (busType == 'Bus Type') {
      final snackBar = SnackBar(
        content: const Text('Select bus type!'),
        action: SnackBarAction(
          label: 'Ok',
          onPressed: () {
            // Some code to undo the change.
          },
        ),
      );
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      return;
    }

    final List<BusModel> busList =
        await provider.getBusBySearchItems(from, to, selectedDate!, busType);
    Navigator.pushNamed(context, SearchBusListPage.roureName,
        arguments: busList);
    print(busList.length);
  }

  void _cancelConfirmation() {
    List<BusModel> busList = provider.busList;
    List<SeatModel> seatList = seatInfoProvider.seatList;
    List<UserModel> userList = userInfoProvider.userList;

    String phone = cancelPhoneController.text;
    String nid = cancelNidController.text;

    List<UserModel> currentUser = [];
    List<SeatModel> currentSeat = [];

    for (int i = 0; i < userList.length; i++) {
      if (phone == userList[i].phone && nid == userList[i].nid) {
        currentUser.add(userList[i]);
        break;
      }
    }

    int availableSeat = 0;
    int index = 0;

    for (int i = 0; i < seatList.length; i++) {
      if (seatList[i].busId == currentUser[0].busId) {
        currentSeat.add(seatList[i]);
        index = i;
        break;
      }
    }

    int busIndex = 0;
    for (int i = 0; i < busList.length; i++) {
      if (busList[i].id == currentUser[0].busId) {
        availableSeat = int.parse(busList[i].availableSeat);
        busIndex = i;
        break;
      }
    }

    String seatNo = currentUser[0].bookedSeat;
    String seatIndex = currentUser[0].bookedSeatIndex;
    List<String> seatNoListString = seatNo.split(',');
    List<String> seatIndexString = seatIndex.split(',');
    seatNoListString.removeAt(seatNoListString.length - 1);
    seatIndexString.removeAt(seatIndexString.length - 1);
    List<int> seatIndexStringIndex = seatIndexString.map(int.parse).toList();

    String allSeatNo = currentSeat[0].seatNo;
    String allSeatindex = currentSeat[0].seatIndex;

    List<String> allSeatNoString = allSeatNo.split(',');
    List<String> allSeatIndexString = allSeatindex.split(',');

    allSeatNoString.removeAt(allSeatNoString.length - 1);
    allSeatIndexString.removeAt(allSeatIndexString.length - 1);

    List<int> allSeatIndexStringIndex =
        allSeatIndexString.map(int.parse).toList();

    for (int i = 0; i < seatIndexStringIndex.length; i++) {
      if (allSeatIndexStringIndex.contains(seatIndexStringIndex[i])) {
        allSeatIndexStringIndex.remove(seatIndexStringIndex[i]);
        allSeatNoString.remove(seatNoListString[i]);
      }
    }

    String updatedIndexList = '';
    String updatedSeatNoList = '';

    for (int i = 0; i < allSeatIndexStringIndex.length; i++) {
      updatedIndexList = '$updatedIndexList${allSeatIndexStringIndex[i]},';
      updatedSeatNoList = '$updatedSeatNoList${allSeatNoString[i]},';
    }

    print(updatedIndexList);
    print(updatedSeatNoList);
    print((availableSeat + seatIndexStringIndex.length).toString());

    provider.updateAvailableSeat(currentUser[0].busId,
        (availableSeat + seatIndexStringIndex.length).toString(), busIndex);
    seatInfoProvider.updateBookedSeatAndIndex(
        updatedSeatNoList, updatedIndexList, currentUser[0].busId, index);
    userInfoProvider.deleteUser(currentUser[0].userId!);

    updatedSeatNoList = '';
  }

  void showSearchDailog() {
    showDialog(
        context: context,
        builder: (context) => AlertDialog(
              title: Text('Enter Bus Name'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  SizedBox(
                    height: 20,
                  ),
                  SizedBox(
                    height: 45,
                    child: TextField(
                      controller: searchController,
                      decoration: InputDecoration(
                        labelText: 'Enter Bus Name',
                        fillColor: Colors.grey[200],
                        filled: true,
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(20.0),
                          borderSide: BorderSide.none,
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                ],
              ),
              actions: [
                ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                      searchResult();
                    },
                    child: Text("Search"),
                    style: ButtonStyle(
                      shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      )),
                      padding:
                          MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                              (Set<MaterialState> states) {
                        return EdgeInsets.only(
                            left: 10, right: 10, top: 10, bottom: 10);
                      }),
                      backgroundColor:
                          MaterialStateProperty.all<Color>(Colors.green),
                    )),
              ],
            ));
  }

  void searchResult() async {
    final List<BusModel> busList =
        await provider.getBusByBusName(searchController.text);
    List<BusModel> searchBusList = [];
    for (int i = 0; i < busList.length; i++) {
      if (searchController.text.toLowerCase() == busList[i].busName) {
        searchBusList.add(busList[i]);
      }
    }

    Navigator.pushNamed(context, SearchBusListPage.roureName,
        arguments: busList);
  }
}
