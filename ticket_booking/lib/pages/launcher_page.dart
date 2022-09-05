import 'package:flutter/material.dart';
import 'package:ticket_booking/authentication.dart';
import 'package:ticket_booking/pages/admin_dashboard_page.dart';
import 'package:ticket_booking/pages/home_page.dart';

class LauncherPage extends StatefulWidget {
  static const String roureName = '/';
  const LauncherPage({Key? key}) : super(key: key);

  @override
  State<LauncherPage> createState() => _LauncherPageState();
}

class _LauncherPageState extends State<LauncherPage> {

  @override
  void initState() {
    getSession().then((value){
      if(value){
        Navigator.pushReplacementNamed(context, AdminDashboardPage.roureName);
      }
      else {
        Navigator.pushReplacementNamed(context, HomePage.roureName);
      }
    });
    super.initState();
  }




  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: const CircularProgressIndicator(

        ),
      ),
    );
  }
}
