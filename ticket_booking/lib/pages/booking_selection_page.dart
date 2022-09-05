

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/model/review_model.dart';
import 'package:ticket_booking/model/seat_data_model.dart';
import 'package:ticket_booking/model/seat_item_model.dart';
import 'package:ticket_booking/model/seat_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/pages/booked_seat_widget.dart';
import 'package:ticket_booking/pages/seat_widget.dart';
import 'package:ticket_booking/pages/user_info_page.dart';
import 'package:ticket_booking/provider/bus_info_provider.dart';
import 'package:ticket_booking/provider/review_info_provider.dart';
import 'package:ticket_booking/provider/seat_info_provider.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';

class BookingSelectionPage extends StatefulWidget {
  static const String roureName = '/booking_selection_page';

  const BookingSelectionPage({Key? key}) : super(key: key);

  @override
  State<BookingSelectionPage> createState() => _BookingSelectionPageState();
}

class _BookingSelectionPageState extends State<BookingSelectionPage> {
  late BusModel busModel;
  Color? selectedColor;
  int? checkedIndex;

  String? seatNoList = '';

  List<SeatItemModel> selectedSeatList = [];

  late SeatInfoProvider seatInfoProvider;
  late String bookedSeatString;
  late List<String> bookedSeatList;
  List <int> bookedSeatIndexList=[];
  List<int> selectedSeatIndex = [];

  late ReviewInfoProvider reviewInfoProvider;
  late BusInfoProvider busInfoProvider;
  late UserInfoProvider userInfoProvider;

  bool haveReview = false;



  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Bus book'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              Container(
                margin: const EdgeInsets.only(top: 10),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Column(
                      children: [
                        const Text(
                          'Free',
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(
                          width: 5,
                        ),
                        Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.grey[300],
                          ),
                          height: 35,
                          width: 70,
                          child: const Icon(Icons.event_seat),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        const Text(
                          'Selected',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(
                          width: 5,
                        ),
                        Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.grey[500],
                          ),
                          height: 35,
                          width: 70,
                          child: const Icon(Icons.event_seat),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        const Text(
                          'Booked',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(
                          width: 5,
                        ),
                        Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.blue,
                          ),
                          height: 35,
                          width: 70,
                          child: const Icon(Icons.event_seat_rounded),
                        )
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 10,),
              Container(
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  border: Border.all(color: Colors.black45)
                ),
                  margin:
                      const EdgeInsets.only(left: 40, right: 40, top: 10, bottom: 10),
                  padding: const EdgeInsets.only(top: 10, left: 5, right: 5),
                  height: 420,
                  width: 400,
                  child: GridView.builder(
                    itemCount: seatItems.length,
                    gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                        crossAxisCount: 4,
                        crossAxisSpacing: 4.0,
                        mainAxisSpacing: 4.0,
                        childAspectRatio: 1.9),
                    itemBuilder: (BuildContext context, int index) {
                      return GestureDetector(
                          onTap: () {
                            setState(() {

                              if(bookedSeatIndexList.length>0){
                                for(int i = 0; i < bookedSeatIndexList.length; i++){
                                  if(index == bookedSeatIndexList[i]){
                                    return;
                                  }
                                }
                              }

                              seatItems[index].isSelected = !seatItems[index].isSelected;
                              if(seatItems[index].isSelected){
                                selectedSeatIndex.add(index);
                              }
                              else if(!seatItems[index].isSelected){
                                selectedSeatIndex.remove(index);
                              }
                            });
                          },

                          child: widgetSelection(index) ? BookedSeatWidget(seatItems[index]) : SeatWidget(seatItems[index]));
                    },
                  )),
              Container(
                margin: const EdgeInsets.all(20.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Bus Name : ${busModel.busName}(${busModel.busUniqueNo})',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'From : ${busModel.from}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'To : ${busModel.to}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'Type : ${busModel.busType}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Fare : ${busModel.fare}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'Date : ${busModel.date}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'Time : ${busModel.time}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          'Available Seat : ${busModel.availableSeat}',
                          style: const TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 50,),
              ElevatedButton(
                  onPressed: () {
                    String seatStateList = '';
                    String seatNoList = '';
                    int count = 0;
                    for(int i = 0; i < seatItems.length; i++){
                      if(seatItems[i].isSelected){
                        seatStateList = '$seatStateList$i,';
                        seatNoList = '$seatNoList${seatItems[i].seatNumber},';
                        count++;
                      }
                    }
                    if(seatStateList == ''){
                      final snackBar = SnackBar(
                        content: const Text('Select seat!'),
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

                    Navigator.pushNamed(context, UserInfoPage.roureName, arguments: ScreenArguments(seatStateList: seatStateList, seatNolist: seatNoList, busModel: busModel, count: count));
                    for(int i =0; i < selectedSeatIndex.length; i++){
                      seatItems[selectedSeatIndex[i]].isSelected = false;
                    }
                  },
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    )),
                    padding:
                        MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states) {
                      return const EdgeInsets.only(
                          left: 100, right: 100, top: 15, bottom: 15);
                    }),
                    backgroundColor:
                        MaterialStateProperty.all<Color>(Colors.green),
                  ),
                  child: const Text("Next")),

              const SizedBox(height: 20,),

              Padding(
                padding: const EdgeInsets.only(left: 28),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: const [
                    Text('Reviews:', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: Colors.green),),
                  ],
                ),
              ),

              Padding(
                padding: const EdgeInsets.only(left: 24, right: 24, bottom: 24, top: 5),
                child: reviewInfoProvider.reviewList.isEmpty ? _dontHaveReview(reviewInfoProvider.reviewList) : _haveReview(reviewInfoProvider.reviewList),
              )

            ],
          ),
        ));
  }

  @override
  void didChangeDependencies() async {
    busModel = ModalRoute.of(context)!.settings.arguments as BusModel;

    seatInfoProvider = Provider.of<SeatInfoProvider>(context);
    busInfoProvider = Provider.of<BusInfoProvider>(context);
    userInfoProvider = Provider.of<UserInfoProvider>(context);
    reviewInfoProvider = Provider.of<ReviewInfoProvider>(context);
    final List<SeatModel> seatModelList = await seatInfoProvider.getSingleBusSeat(busModel.id!);

    bookedSeatString = seatModelList[0].seatIndex;
    bookedSeatList = bookedSeatString.split(",");
    bookedSeatList.removeAt(bookedSeatList.length-1);
    bookedSeatIndexList = bookedSeatList.map(int.parse).toList();

    super.didChangeDependencies();
  }

  bool widgetSelection(int index){
    print(bookedSeatIndexList);
    for(int i = 0; i < bookedSeatIndexList.length; i++){
      if(index == bookedSeatIndexList[i]){
        return true;
      }
    }
    return false;
  }

  Widget _haveReview(List<ReviewModel> reviewList){

    List<UserModel> userList = userInfoProvider.userList;
    List<BusModel> busList = busInfoProvider.busList;
    List<ReviewModel> newReviewList = [];
    List<UserModel> newUserList = [];

    for(int i = 0; i < reviewList.length; i++){
      if(reviewList[i].busId == busModel.id){
        newReviewList.add(reviewList[i]);
      }
    }

    return Container(
      padding: const EdgeInsets.all(0),
      height: 300,
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
        // color: Colors.grey[200],
        borderRadius: BorderRadius.circular(10),
      ),


      child: newReviewList.isEmpty ? _dontHaveReview(newReviewList) : ListView.builder(itemCount: newReviewList.length, itemBuilder: (context, int index){

        final review = newReviewList[index];

        return Card(
          elevation: 5,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Icon(Icons.person),
                const SizedBox(height: 10,),
                Text(review.review),
              ],
            ),
          ),
        );
      })
    );
  }



  Widget _dontHaveReview(List<ReviewModel> reviewList){
    return Container(
      height: 300,
      width: MediaQuery.of(context).size.width,
      decoration: BoxDecoration(
        color: Colors.grey[200],
        borderRadius: BorderRadius.circular(10),
      ),
      child: const Center(child: const Text('No Review Found'))
    );
  }

  void showReview(List<ReviewModel> newReviewList) {

  }

}
class ScreenArguments {
  String seatStateList;
  String seatNolist;
  BusModel busModel;
  int count;

  ScreenArguments({required this.seatStateList, required this.seatNolist, required this.busModel, required this.count});

}


