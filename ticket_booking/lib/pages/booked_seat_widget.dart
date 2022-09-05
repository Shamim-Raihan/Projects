import 'package:flutter/material.dart';

import '../model/seat_item_model.dart';

class BookedSeatWidget extends StatefulWidget {
  SeatItemModel seatItemModel;


  BookedSeatWidget(this.seatItemModel);

  @override
  State<BookedSeatWidget> createState() => _BookedSeatWidgetState();
}

class _BookedSeatWidgetState extends State<BookedSeatWidget> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          color: Colors.blue,
        ),
        height: 70,
        width: 70,
        child: SingleChildScrollView(
          child: Column(
            children: [
              Icon(Icons.event_seat),
              Text(widget.seatItemModel.seatNumber),
            ],
          ),
        ),
      ),
    );
  }
}
