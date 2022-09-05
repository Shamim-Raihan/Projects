import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:expansion_tile_card/expansion_tile_card.dart';
import 'package:ticket_booking/model/review_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:ticket_booking/provider/review_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';
import 'package:rating_dialog/rating_dialog.dart';

import 'booking_selection_page.dart';

class SearchBusListPage extends StatefulWidget {
  static const String roureName = '/search_bus_list_page';

  const SearchBusListPage({Key? key}) : super(key: key);

  @override
  State<SearchBusListPage> createState() => _SearchBusListPageState();
}

class _SearchBusListPageState extends State<SearchBusListPage> {
  late List<BusModel> busList;

  final ratingPhoneController = TextEditingController();
  final ratingNidController = TextEditingController();
  late UserInfoProvider userInfoProvider;
  late ReviewInfoProvider reviewInfoProvider;
  late BusInfoProvider busInfoProvider;


  @override
  void dispose() {
    ratingPhoneController.dispose();
    ratingNidController.dispose();
    super.dispose();
  }

  List<UserModel> userList = [];

  @override
  void didChangeDependencies() {
    busList = ModalRoute.of(context)!.settings.arguments as List<BusModel>;
    userInfoProvider = Provider.of<UserInfoProvider>(context);
    reviewInfoProvider = Provider.of<ReviewInfoProvider>(context);
    busInfoProvider = Provider.of<BusInfoProvider>(context);
    userList = userInfoProvider.userList;
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
          title: Text('Search Result'),
        ),
        body: busList.length <= 0 ? Center(child: Text('No bus Found', style: TextStyle(fontSize: 20),)) : ListView.builder(
          itemCount: busList.length,
          itemBuilder: (context, int index) {
            final bus = busList[index];
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Card(
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
                  title: Text(
                    'BUS : ' + bus.busName.toUpperCase(),
                    style: TextStyle(
                      fontSize: 20,
                    ),
                  ),
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
                                  Text(
                                    'From: ' + bus.from,
                                    style: TextStyle(),
                                  ),
                                  Text(
                                    'From: ' + bus.to,
                                    style: TextStyle(),
                                  ),
                                  Text(
                                    'Bus type: ' + bus.busType,
                                    style: TextStyle(),
                                  ),
                                ],
                              ),
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    'Time: ' + bus.time,
                                    style: TextStyle(),
                                  ),
                                  Text(
                                    'Date: ' + bus.date,
                                    style: TextStyle(),
                                  ),
                                  Text(
                                    'Fare: ' + bus.fare,
                                    style: TextStyle(),
                                  ),
                                ],
                              ),
                            ],
                          )),
                    ),
                    ButtonBar(
                      alignment: MainAxisAlignment.spaceAround,
                      buttonHeight: 52.0,
                      buttonMinWidth: 90.0,
                      children: <Widget>[
                        TextButton(
                          style: flatButtonStyle,
                          onPressed: () {
                            giveRating(index);
                          },
                          child: Column(
                            children: <Widget>[
                              Icon(Icons.star_border),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(vertical: 2.0),
                              ),
                              Text('RATING & REVIEW'),
                            ],
                          ),
                        ),
                        TextButton(
                          style: flatButtonStyle,
                          onPressed: () {
                            Navigator.pushNamed(
                                context, BookingSelectionPage.roureName,
                                arguments: bus);
                          },
                          child: Column(
                            children: <Widget>[
                              Icon(Icons.navigate_next_outlined),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(vertical: 2.0),
                              ),
                              Text('NEXT'),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            );
          },
        ));
  }

  void giveRating(int index) {
    showDialog(
        context: context,
        builder: (context) => AlertDialog(
              title: Text('User Authentication!!'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  SizedBox(
                    height: 20,
                  ),
                  SizedBox(
                    height: 45,
                    child: TextField(
                      controller: ratingPhoneController,
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
                      controller: ratingNidController,
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
                      Navigator.pop(context);
                      updateRatingInfo(index);
                    },
                    child: Text("Ok"),
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

  void updateRatingInfo(int index) {
    String phone = ratingPhoneController.text;
    String nid = ratingNidController.text;

    bool isAuthUser = false;
    int? userId;
    int? ratingCount = busList[index].ratingCount;
    String rating = busList[index].rating;

    for (int i = 0; i < userList.length; i++) {
      if (phone == userList[i].phone && nid == userList[i].nid) {
        isAuthUser = !isAuthUser;
        userId = userList[i].userId;
        break;
      }
    }

    if (isAuthUser) {
      showDialog(
        context: context,
        barrierDismissible: true, // set to false if you want to force a rating
        builder: (context) => RatingDialog(
          initialRating: 1.0,
          title: const Text(
            'Share your Experience',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 25,
              fontWeight: FontWeight.bold,
            ),
          ),
          message: const Text(
            'Tap a star to set your rating. Add more description here if you want.',
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 15),
          ),
          submitButtonText: 'Submit',
          commentHint: 'Add comment',
          onCancelled: () => print('cancelled'),
          onSubmitted: (response) {
            // print('rating: ${response.rating}, comment: ${response.comment}');
            int updateRatingCount = ratingCount + 1;
            double dRating = double.parse(rating);
            double? updateRating;
            if(dRating == 0.0){
              updateRating = response.rating;
            }
            else {
              double preRating = dRating * ratingCount;
              updateRating = preRating + response.rating / updateRatingCount;
            }
            busInfoProvider.updateRating(busList[index].id!, index, updateRating.toString());
            reviewInfoProvider.addNewReview(ReviewModel(review: response.comment, userId: userId!, busId: busList[index].id!));

          },
        ),
      );
    } else {
      final snackBar = SnackBar(
        content: const Text('You are not under service'),
        action: SnackBarAction(
          label: 'Ok',
          onPressed: () {},
        ),
      );
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      return;
    }
  }

  final _dialog = RatingDialog(
    initialRating: 1.0,
    title: const Text(
      'Share your Experience',
      textAlign: TextAlign.center,
      style: TextStyle(
        fontSize: 25,
        fontWeight: FontWeight.bold,
      ),
    ),
    message: const Text(
      'Tap a star to set your rating. Add more description here if you want.',
      textAlign: TextAlign.center,
      style: TextStyle(fontSize: 15),
    ),
    submitButtonText: 'Submit',
    commentHint: 'Add comment',
    onCancelled: () => print('cancelled'),
    onSubmitted: (response) {
      // print('rating: ${response.rating}, comment: ${response.comment}');

    },
  );
}
























