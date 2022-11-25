import librosa,librosa.display
import ruptures as rpt  

def calculation():
    y, sr = librosa.load('./Faded_Alan Walker.mp3')
    cent = librosa.feature.spectral_centroid(y=y, sr=sr)

    signal = cent
    signal = signal.T

    # detection
    algo = rpt.Dynp(model="l2").fit(signal)
    result = algo.predict(n_bkps=7)

    print(result)

    return result