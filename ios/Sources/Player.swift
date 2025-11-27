import Foundation
import AVFoundation

class SimplePlayer {
    private var player: AVPlayer?

    func play(url: URL) {
        player = AVPlayer(url: url)
        player?.play()
    }

    func pause() {
        player?.pause()
    }

    func stop() {
        player = nil
    }
}
