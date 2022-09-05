class SeatModel {
  int? seatId;
  String seatNo;
  String seatIndex;
  int busId;

  SeatModel(
      {this.seatId,
      required this.seatNo,
      required this.seatIndex,
      required this.busId});

  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      seatTableColSeatNo: seatNo,
      seatTableColSeatIndex: seatIndex,
      seatTableColBusId: busId,
    };
    if (seatId != null) {
      map[seatTableColSeatId] = seatId;
    }
    return map;
  }

  factory SeatModel.fromMap(Map<String, dynamic> map) => SeatModel(
      seatId: map[seatTableColSeatId],
      seatNo: map[seatTableColSeatNo],
      seatIndex: map[seatTableColSeatIndex],
      busId: map[seatTableColBusId]);
}

const String seatTableName = 'seat_table';

const String seatTableColSeatId = 'seat_id';
const String seatTableColSeatNo = 'booked_seat_no';
const String seatTableColSeatIndex = 'booked_seat_index';
const String seatTableColBusId = 'bus_id';
