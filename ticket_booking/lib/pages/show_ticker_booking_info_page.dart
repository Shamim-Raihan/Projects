import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:expansion_tile_card/expansion_tile_card.dart';
import 'package:ticket_booking/provider/review_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';

import '../provider/seat_info_provider.dart';
import 'booked_user_list_page.dart';

class ShowTicketBookingInfoPage extends StatefulWidget {
  static const String roureName = '/show_user_info';

  const ShowTicketBookingInfoPage({Key? key}) : super(key: key);

  @override
  State<ShowTicketBookingInfoPage> createState() =>
      _ShowTicketBookingInfoPageState();
}

class _ShowTicketBookingInfoPageState extends State<ShowTicketBookingInfoPage> {

  List<BusModel> busList = [];

  late BusInfoProvider busInfoProvider;
  late ReviewInfoProvider reviewInfoProvider;
  late SeatInfoProvider seatInfoProvider;
  late UserInfoProvider userInfoProvider;


  @override
  void didChangeDependencies() {
    busInfoProvider = Provider.of<BusInfoProvider>(context);
    reviewInfoProvider = Provider.of<ReviewInfoProvider>(context);
    seatInfoProvider = Provider.of<SeatInfoProvider>(context);
    userInfoProvider = Provider.of<UserInfoProvider>(context);

    busList = busInfoProvider.busList;
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {

    final ButtonStyle flatButtonStyle = TextButton.styleFrom(
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(4.0)),
      ),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text('User Information'),
      ),
      body: busList.length <= 0 ? Center(child: Text('No bus added yet', style: TextStyle(fontSize: 25),)) : Consumer<BusInfoProvider>(
        builder: (context, provider, _) => ListView.builder(
          padding: EdgeInsets.all(8),
          itemCount: provider.busList.length,
          itemBuilder: (context, index){
            final bus = provider.busList[index];
            return Card(
              elevation: 5,
              child: ExpansionTileCard(
                leading: CircleAvatar(
                  child: Center(
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(Icons.star, size: 15,),
                        Text(busList[index].rating, style: TextStyle(fontSize: 14),)
                      ],
                    ),
                  ),
                ),
                title: Text('Bus : ' + bus.busName.toUpperCase(), style: TextStyle(fontSize: 20,),),
                subtitle: Text('Available Seat : ' + bus.availableSeat),
                children: <Widget>[
                  Divider(
                    thickness: 2.0,
                    height: 2.0,
                  ),
                  Align(
                    alignment: Alignment.centerLeft,
                    child: Padding(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 16.0,
                        vertical: 8.0,
                      ),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('From: ' + bus.from, style: TextStyle(),),
                              Text('From: ' + bus.to, style: TextStyle(),),
                              Text('Bus type: ' + bus.busType, style: TextStyle(),),
                            ],
                          ),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('Time: ' + bus.time, style: TextStyle(),),
                              Text('Date: ' + bus.date, style: TextStyle(),),
                              Text('Fare: ' + bus.fare, style: TextStyle(),),
                            ],
                          ),
                        ],
                      )
                    ),
                  ),
                  ButtonBar(
                    alignment: MainAxisAlignment.spaceAround,
                    buttonHeight: 52.0,
                    buttonMinWidth: 90.0,
                    children: <Widget>[

                      TextButton(
                        style: flatButtonStyle,
                        onPressed: () {
                          deleteBus(bus.id!);
                        },
                        child: Column(
                          children: <Widget>[
                            Icon(Icons.delete),
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 2.0),
                            ),
                            Text('Delete Bus'),
                          ],
                        ),
                      ),

                      TextButton(
                        style: flatButtonStyle,
                        onPressed: () {
                          Navigator.pushNamed(context, BookedUserListPage.roureName, arguments: bus.id);
                        },
                        child: Column(
                          children: <Widget>[
                            Icon(Icons.remove_red_eye),
                            Padding(
                              padding: const EdgeInsets.symmetric(vertical: 2.0),
                            ),
                            Text('Show Users'),
                          ],
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            );
          },
        )
      ),
    );
  }

  void deleteBus(int busId) {

    showAnimatedDialog(
      context: context,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return ClassicGeneralDialogWidget(
          titleText: 'Delete Bus!!!',
          contentText: 'Are You Sure?',
          onPositiveClick: () {
            Navigator.of(context).pop();
            busInfoProvider.deleteBusByBusId(busId);
            reviewInfoProvider.deleteReviewByBusId(busId);
            seatInfoProvider.deleteSeatByBusId(busId);
            userInfoProvider.deleteUserByBusId(busId);
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
}

