import 'package:flutter/material.dart';
import 'package:ticket_booking/authentication.dart';
import 'package:ticket_booking/pages/add-new_bus_page.dart';
import 'package:ticket_booking/pages/home_page.dart';
import 'package:ticket_booking/pages/show_ticker_booking_info_page.dart';

class AdminDashboardPage extends StatefulWidget {
  static const String roureName = '/admin_dashboard_page';
  const AdminDashboardPage({Key? key}) : super(key: key);

  @override
  State<AdminDashboardPage> createState() => _AdminDashboardPageState();
}

class _AdminDashboardPageState extends State<AdminDashboardPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Admin Dashboard'),
        actions: [
          PopupMenuButton<int>(
            itemBuilder: (context) => [
              PopupMenuItem(
                value: 1,
                child: Row(
                  children: [
                    Icon(Icons.logout),
                    SizedBox(
                      width: 10,
                    ),
                    Text("Logout")
                  ],
                ),
              ),
            ],
            offset: Offset(0, 100),
            color: Colors.grey,
            elevation: 5,
            onSelected: (value) {
              if (value == 1) {
                setSession(false).then((value){
                  Navigator.pushReplacementNamed(context, HomePage.roureName);
                });
              }
            },
          ),
        ],
      ),

      body: Center(
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.only(top: 80),
              child: ElevatedButton(onPressed: (){
                Navigator.pushNamed(context, AddNewBusPage.roureName);
              }, child: Text("Add new bus"),
                  style: ButtonStyle(
                      shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30.0),

                          )
                      ),
                    padding: MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                        (Set<MaterialState> states){
                          return EdgeInsets.only(left: 120, right: 120, top: 20, bottom: 20);
                        }
                    ),
                    backgroundColor: MaterialStateProperty.all<Color>(Colors.green),
                  )
              ),
            ),
            SizedBox(height: 30,),
            Container(
              margin: EdgeInsets.only(top: 10),
              child: ElevatedButton(onPressed: (){
                Navigator.pushNamed(context, ShowTicketBookingInfoPage.roureName);
              }, child: Text("Customer Information"),
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),

                        )
                    ),
                    padding: MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states){
                          return EdgeInsets.only(left: 90, right: 90, top: 20, bottom: 20);
                        }
                    ),
                    backgroundColor: MaterialStateProperty.all<Color>(Colors.green),
                  )
              ),
            ),
          ],
        ),
      ),
    );
  }
}
