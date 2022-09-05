import 'package:flutter/material.dart';
import 'package:path/path.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/pages/add-new_bus_page.dart';
import 'package:ticket_booking/pages/admin_dashboard_page.dart';
import 'package:ticket_booking/pages/admin_login_page.dart';
import 'package:ticket_booking/pages/booked_user_list_page.dart';
import 'package:ticket_booking/pages/booking_selection_page.dart';
import 'package:ticket_booking/pages/home_page.dart';
import 'package:ticket_booking/pages/launcher_page.dart';
import 'package:ticket_booking/pages/search_bus_list_page.dart';
import 'package:ticket_booking/pages/show_ticker_booking_info_page.dart';
import 'package:ticket_booking/pages/ticket_cancelation_page.dart';
import 'package:ticket_booking/pages/ticket_page.dart';
import 'package:ticket_booking/pages/user_details_page.dart';
import 'package:ticket_booking/pages/user_info_page.dart';
import 'package:ticket_booking/provider/admin_info_provider.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:ticket_booking/provider/review_info_provider.dart';
import 'package:ticket_booking/provider/seat_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';

void main() {
  runApp(MultiProvider(providers: [
    ChangeNotifierProvider(create: (context) => AdminInfoProvider()),
    ChangeNotifierProvider(create: (context) => BusInfoProvider()..getAllBus()),
    ChangeNotifierProvider(create: (context) => SeatInfoProvider()..getAllSeat()),
    ChangeNotifierProvider(create: (context) => UserInfoProvider()..getAllUser()),
    ChangeNotifierProvider(create: (context) => ReviewInfoProvider()..getAllReview()),
  ],
      child: const MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.green,
      ),
      initialRoute: LauncherPage.roureName,
      routes: {
        HomePage.roureName : (context) => HomePage(),
        AdminLogInPage.roureName : (context) => AdminLogInPage(),
        LauncherPage.roureName : (context) => LauncherPage(),
        AdminDashboardPage.roureName : (context) => AdminDashboardPage(),
        AddNewBusPage.roureName : (context) => AddNewBusPage(),
        SearchBusListPage.roureName : (context) => SearchBusListPage(),
        BookingSelectionPage.roureName : (context) => BookingSelectionPage(),
        UserInfoPage.roureName : (context) => UserInfoPage(),
        TicketPage.roureName : (context) => TicketPage(),
        ShowTicketBookingInfoPage.roureName : (context) => ShowTicketBookingInfoPage(),
        BookedUserListPage.roureName : (context) => BookedUserListPage(),
        UserDetailsPage.roureName : (context) => UserDetailsPage(),
        TicketCancelationPage.roureName : (context) => TicketCancelationPage(),
      },
    );

  }
}


