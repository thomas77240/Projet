from flask import Flask, request, send_file, render_template
from pytube import YouTube
import os
import tempfile
import uuid

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/download', methods=['POST'])
def download_video():
    url = request.form['url']
    try:
        yt = YouTube(url)
        stream = yt.streams.get_highest_resolution()

        # Utiliser le titre de la vidéo comme nom de fichier
        title = yt.title
        file_name = f"{title}.mp4"

        # Utiliser un fichier temporaire avec un nom unique
        temp_dir = tempfile.gettempdir()
        file_path = os.path.join(temp_dir, file_name)

        # Télécharger la vidéo avec le titre comme nom de fichier
        stream.download(output_path=temp_dir, filename=file_name)

        # Vérifier si le fichier a été correctement téléchargé
        if os.path.exists(file_path):
            return send_file(file_path, as_attachment=True)
        else:
            return "Le fichier n'a pas été téléchargé correctement.", 500
    except Exception as e:
        return f"Erreur: {str(e)}", 500

if __name__ == '__main__':
    app.run(debug=True)

