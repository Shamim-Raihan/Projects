import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/model/seat_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/pages/booking_selection_page.dart';
import 'package:ticket_booking/pages/ticket_page.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:ticket_booking/provider/seat_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';

class UserInfoPage extends StatefulWidget {
  static const String roureName = '/user_info_page';

  const UserInfoPage({Key? key}) : super(key: key);

  @override
  State<UserInfoPage> createState() => _UserInfoPageState();
}

class _UserInfoPageState extends State<UserInfoPage> {
  late ScreenArguments args;

  final nameController = TextEditingController();
  final emailController = TextEditingController();
  final phoneController = TextEditingController();
  final nidController = TextEditingController();

  @override
  void dispose() {
    nameController.dispose();
    emailController.dispose();
    phoneController.dispose();
    nidController.dispose();
    super.dispose();
  }

  late SeatInfoProvider seatInfoProvider;
  late UserInfoProvider userInfoProvider;
  late BusInfoProvider busInfoProvider;
  late String seatNoList;

  @override
  void didChangeDependencies() {
    args = ModalRoute.of(context)!.settings.arguments as ScreenArguments;

    seatInfoProvider = Provider.of<SeatInfoProvider>(context);
    userInfoProvider = Provider.of<UserInfoProvider>(context);
    busInfoProvider = Provider.of<BusInfoProvider>(context);
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('User Information'),
      ),
      body: SingleChildScrollView(
        child: Container(
          padding: EdgeInsets.only(left: 20, right: 20),
          child: Column(
            children: [
              SizedBox(
                height: 20,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Column(
                    children: [
                      Text(
                        'Welcome To Our Service',
                        style: TextStyle(
                            fontSize: 25,
                            fontWeight: FontWeight.bold,
                            color: Colors.green),
                      ),
                      Text(
                        'Give Your Information for Ticket Confirmation',
                        style: TextStyle(
                          fontSize: 16,
                        ),
                      ),
                    ],
                  )
                ],
              ),
              SizedBox(
                height: 30,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Bus Name : ' +
                            args.busModel.busName +
                            '(${args.busModel.busUniqueNo})',
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'From : ' + args.busModel.from,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'To : ' + args.busModel.to,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'Type : ' + args.busModel.busType,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Fare : ' + args.busModel.fare,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'Date : ' + args.busModel.date,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'Time : ' + args.busModel.time,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                      Text(
                        'Available Seat : ' + args.busModel.availableSeat,
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                ],
              ),
              SizedBox(
                height: 30,
              ),

              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Selected Seat : ${args.count}',
                    style: TextStyle(
                        fontWeight: FontWeight.bold, color: Colors.blue),
                  ),
                  Container(
                    width: 100,
                    child: SingleChildScrollView(
                      scrollDirection: Axis.horizontal,
                      child: Text(
                        'Seat No : ${args.seatNolist}',
                        style: TextStyle(
                            fontWeight: FontWeight.bold, color: Colors.amber),
                      ),
                    ),
                  ),


                  Text(
                    'Total Fare : ${int.parse(args.busModel.fare) * args.count}',
                    style: TextStyle(
                        fontWeight: FontWeight.bold, color: Colors.red),
                  ),
                ],
              ),
              SizedBox(
                height: 30,
              ),
              Column(
                children: [
                  TextField(
                    controller: nameController,
                    decoration: InputDecoration(
                      labelText: 'Enter name',
                      fillColor: Colors.grey[200],
                      filled: true,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  TextField(
                    controller: emailController,
                    decoration: InputDecoration(
                      labelText: 'Enter Email',
                      fillColor: Colors.grey[200],
                      filled: true,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  TextField(
                    controller: phoneController,
                    decoration: InputDecoration(
                      labelText: 'Enter Phone',
                      fillColor: Colors.grey[200],
                      filled: true,
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20.0),
                        borderSide: BorderSide.none,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  TextField(
                    controller: nidController,
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
                ],
              ),



              SizedBox(
                height: 50,
              ),
              ElevatedButton(
                  onPressed: () {
                    saveAllRespectiveData();

                  },
                  child: Text("Confirm Ticket Booking"),
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    )),
                    padding:
                        MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states) {
                      return EdgeInsets.only(
                          left: 80, right: 80, top: 15, bottom: 15);
                    }),
                    backgroundColor:
                        MaterialStateProperty.all<Color>(Colors.green),
                  )),
            ],
          ),
        ),
      ),
    );
  }




  void saveAllRespectiveData() async {


    if(nameController.text.isEmpty){
      final snackBar = SnackBar(
        content: const Text('Enter your name!'),
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
    if(emailController.text.isEmpty){
      final snackBar = SnackBar(
        content: const Text('Enter your email!'),
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

    if(phoneController.text.isEmpty){
      final snackBar = SnackBar(
        content: const Text('Enter your phone number!'),
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

    if(nidController.text.isEmpty){
      final snackBar = SnackBar(
        content: const Text('Enter your NID!'),
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
          titleText: 'Title',
          contentText: 'Are You Sure?',
          onPositiveClick: () {
            _bookConfirmation();
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

  void _bookConfirmation() async {
    int index = 0;
    for(int i =0; i < busInfoProvider.busList.length; i++){
      if(busInfoProvider.busList[i].id == args.busModel.id){
        index = i;
        break;
      }
    }

    int checker = 0;
    List<SeatModel> seatList = await seatInfoProvider.seatList;
    print('seat list length ${seatList.length}');

    for (int i = 0; i < seatList.length; i++) {
      if (seatList[i].busId == args.busModel.id) {
        checker = 1;
        print(seatList[i].busId);
        await seatInfoProvider.updateBookedSeatAndIndex(
            seatList[i].seatNo + args.seatNolist,
            seatList[i].seatIndex + args.seatStateList,
            args.busModel.id!, i);
        break;
      }
    }
    print(checker);
    if(checker == 0){
      final seat = SeatModel(
          seatNo: args.seatNolist,
          seatIndex: args.seatStateList,
          busId: args.busModel.id!);
      final status = await seatInfoProvider.addNewSeat(seat);
      print('add new seat ${status}');
    }
    final user = UserModel(
        name: nameController.text,
        email: emailController.text,
        phone: phoneController.text,
        nid: nidController.text,
        bookedSeat: args.seatNolist,
        bookedSeatIndex: args.seatStateList,
        busId: args.busModel.id!);
    final status2 = await userInfoProvider.addNewUser(user);
    await busInfoProvider.updateAvailableSeat(args.busModel.id!,
        '${(int.parse(args.busModel.availableSeat) - args.count)}', index);
    Navigator.pushReplacementNamed(context, TicketPage.roureName, arguments: ScreenArgument2(userModel: user, busModel: args.busModel, seatNoList: args.seatNolist));
  }
}


class ScreenArgument2 {
  UserModel userModel;
  BusModel busModel;
  String seatNoList;

  ScreenArgument2({required this.userModel, required this.busModel, required this.seatNoList});
}



























