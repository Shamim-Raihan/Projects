import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/pages/admin_dashboard_page.dart';
import 'package:ticket_booking/pages/booked_user_list_page.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';

class UserDetailsPage extends StatefulWidget {
  static const String roureName = '/user_details_page';

  const UserDetailsPage({Key? key}) : super(key: key);

  @override
  State<UserDetailsPage> createState() => _UserDetailsPageState();
}

class _UserDetailsPageState extends State<UserDetailsPage> {

  late UserModel userModel;
  late BusInfoProvider busInfoProvider;
  List<BusModel> busList = [];
  List<BusModel> singleBusList = [];

  @override
  void didChangeDependencies() {
    userModel = ModalRoute.of(context)!.settings.arguments as UserModel;

    busInfoProvider = Provider.of<BusInfoProvider>(context);
    busList = busInfoProvider.busList;

    for(int i = 0; i < busList.length; i++){
      if(busList[i].id == userModel.busId){
        singleBusList.add(busList[i]);
      }
    }

    // print(singleBusList.length);

    super.didChangeDependencies();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('User Datails'),),
      body:Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            children: [
              Image.network('https://people.eecs.berkeley.edu/~hazem.torfah/img/s_akshay.png', height: 120, width: 120,),
              Text('Passenger Details', style: TextStyle(fontSize: 20, fontWeight: FontWeight.w500, color: Colors.green),),
              SizedBox(height: 30,),
              Container(
                height: 70,
                child: Card(
                  color: Colors.grey[200],
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Text('Name : ${userModel.name}', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),),
                      ],
                    ),
                  ),
                ),
              ),

              Container(
                height: 70,
                child: Card(
                  color: Colors.grey[200],
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Text('Contact : ${userModel.phone}', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),),
                      ],
                    ),
                  ),
                ),
              ),

              Container(
                height: 70,
                child: Card(
                  color: Colors.grey[200],
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Text('NID : ${userModel.nid}', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),),
                      ],
                    ),
                  ),
                ),
              ),

              Container(
                height: 70,
                child: Card(
                  color: Colors.grey[200],
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Text('Seat Number : ${userModel.bookedSeat}', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),),
                      ],
                    ),
                  ),
                ),
              ),

              Container(
                height: 70,
                child: Card(
                  color: Colors.grey[200],
                  elevation: 3,
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      children: [
                        Text('Email : ${userModel.email}', style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),),
                      ],
                    ),
                  ),
                ),
              ),
              SizedBox(height: 30,),
              ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context, BookedUserListPage.roureName);
                  },
                  child: Text("Back to User List"),
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        )),
                    padding:
                    MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states) {
                          return EdgeInsets.only(
                              left: 100, right: 100, top: 15, bottom: 15);
                        }),
                    backgroundColor:
                    MaterialStateProperty.all<Color>(Colors.green),
                  )),
            ],
          ),
        ),

    );
  }
}
