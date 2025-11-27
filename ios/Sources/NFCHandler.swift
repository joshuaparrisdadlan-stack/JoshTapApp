import Foundation
import CoreNFC
import AVFoundation

class NFCHandler: NSObject, NFCNDEFReaderSessionDelegate {
    private var session: NFCNDEFReaderSession?
    func beginSession() {
        guard NFCNDEFReaderSession.readingAvailable else { return }
        session = NFCNDEFReaderSession(delegate: self, queue: nil, invalidateAfterFirstRead: false)
        session?.begin()
    }

    func readerSession(_ session: NFCNDEFReaderSession, didInvalidateWithError error: Error) {
        print("NFC invalidated: \(error)")
    }

    func readerSession(_ session: NFCNDEFReaderSession, didDetectNDEFs messages: [NFCNDEFMessage]) {
        for message in messages {
            for record in message.records {
                if let uri = String(data: record.payload, encoding: .utf8) {
                    print("Found payload: \(uri)")
                }
            }
        }
    }
}
