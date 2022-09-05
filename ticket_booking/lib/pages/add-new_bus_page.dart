import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:intl/src/intl/date_format.dart';
import 'dart:io';

import '../provider/bus_info_provider.dart';

class AddNewBusPage extends StatefulWidget {
  static const String roureName = '/add_new_bus_page';

  const AddNewBusPage({Key? key}) : super(key: key);

  @override
  State<AddNewBusPage> createState() => _AddNewBusPageState();
}

class _AddNewBusPageState extends State<AddNewBusPage> {
  late BusInfoProvider provider;

  @override
  void didChangeDependencies() {
    provider = Provider.of<BusInfoProvider>(context);
    super.didChangeDependencies();
  }

  late String from;
  late String to;
  late String busType;

  // List of items in our dropdown menu
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

  final busNameController = TextEditingController();
  final fareController = TextEditingController();
  final totalSeatController = TextEditingController();
  final uniqueNoController = TextEditingController();

  @override
  void dispose() {
    busNameController.dispose();
    fareController.dispose();
    totalSeatController.dispose();
    uniqueNoController.dispose();
    super.dispose();
  }

  String? selectedTime = TimeOfDay.now().hourOfPeriod.toString() +
      ':' +
      TimeOfDay.now().minute.toString() +
      ' ' +
      "${TimeOfDay.now().period}".split('.')[1].toString();

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

  String? selectedDate = DateFormat('dd/MM/yyyy').format(DateTime.now());

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(DateTime.now().year, 1),
        lastDate: DateTime(2101));
    if (picked != null && picked != selectedDate) {
      setState(() {
        selectedDate = DateFormat('dd/MM/yyyy').format(picked);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Add new Bus'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
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
                                  child: new Text(items),
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
                child: TextField(
                  controller: busNameController,
                  decoration: InputDecoration(
                    labelText: 'Enter Bus name',
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
              Padding(
                padding: const EdgeInsets.only(left: 20, right: 20),
                child: TextField(
                  controller: fareController,
                  decoration: InputDecoration(
                    labelText: 'Fare Amount',
                    fillColor: Colors.grey[200],
                    filled: true,
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none),
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              Padding(
                padding: const EdgeInsets.only(left: 20, right: 20),
                child: TextField(
                  controller: totalSeatController,
                  decoration: InputDecoration(
                    labelText: 'Seat Number',
                    fillColor: Colors.grey[200],
                    filled: true,
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none),
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              Padding(
                padding: const EdgeInsets.only(left: 20, right: 20),
                child: TextField(
                  controller: uniqueNoController,
                  decoration: InputDecoration(
                    labelText: 'Bus number',
                    fillColor: Colors.grey[200],
                    filled: true,
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none),
                  ),
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
                      Text('$selectedDate'),
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
                      Text('$selectedTime'),
                      SizedBox(
                        height: 20.0,
                      ),
                      TextButton(
                        onPressed: () => _selectTime(context),
                        child: Text('Select Time'),
                      ),
                    ],
                  ),
                ),
              ),
              SizedBox(
                height: 30,
              ),
              ElevatedButton(
                  onPressed: () {

                    _saveBusInfo();
                  },
                  child: Text("Save"),
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    )),
                    padding:
                        MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states) {
                      return EdgeInsets.only(
                          left: 155, right: 155, top: 15, bottom: 15);
                    }),
                    backgroundColor:
                        MaterialStateProperty.all<Color>(Colors.green),
                  )),
              SizedBox(
                height: 30,
              ),
            ],
          ),
        ));
  }

  void _saveBusInfo() async {
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
        content: const Text('Select different location'),
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

    if (busNameController.text.isEmpty || busNameController.text == null) {
      final snackBar = SnackBar(
        content: const Text('Enter bus name!'),
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

    if (fareController.text.isEmpty || fareController.text == null) {
      final snackBar = SnackBar(
        content: const Text('Enter fare amount!'),
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

    if (totalSeatController.text.isEmpty || totalSeatController.text == null) {
      final snackBar = SnackBar(
        content: const Text('Enter seat number!'),
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

    if (uniqueNoController.text.isEmpty || uniqueNoController.text == null) {
      final snackBar = SnackBar(
        content: const Text('Enter bus number!'),
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


    showAnimatedDialog(
      context: context,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return ClassicGeneralDialogWidget(
          titleText: 'Add new Bus',
          contentText: 'Are You Sure?',
          onPositiveClick: () {
            _saveBus();
            Navigator.of(context).pop();
          },
          onNegativeClick: () {
            Navigator.of(context).pop();
          },
        );
      },
      animationType: DialogTransitionType.size,
      curve: Curves.fastOutSlowIn,
      duration: Duration(milliseconds: 500),
    );


  }

  void _saveBus() async {
    final bus = BusModel(
      from: from,
      to: to,
      busName: busNameController.text,
      date: selectedDate.toString(),
      time: selectedTime.toString(),
      fare: fareController.text,
      busType: busType,
      totalSeat: totalSeatController.text,
      availableSeat: totalSeatController.text,
      busUniqueNo: uniqueNoController.text,
      rating: '0.0',
      ratingCount: 0,
    );
    final status = await provider.addNewBus(bus);
    if (status) {
      Navigator.pop(context);
    }
  }
}
