import 'package:flutter/material.dart';
import 'package:ticket_booking/model/seat_item_model.dart';

import '../model/seat_data_model.dart';

class SeatWidget extends StatefulWidget {
  // bool isSelected;
  // String seatNo;


  SeatItemModel seatItemModel;


  SeatWidget(this.seatItemModel);

  @override
  State<SeatWidget> createState() => _SeatWidgetState();
}

class _SeatWidgetState extends State<SeatWidget> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          color: widget.seatItemModel.isSelected ? Colors.grey[500] : Colors.grey[300],
        ),
        height: 70,
        width: 70,
        child: SingleChildScrollView(
          child: Column(
            children: [
              Icon(Icons.event_seat,),
              Text(widget.seatItemModel.seatNumber),
            ],
          ),
        ),
      ),
    );
  }
}
