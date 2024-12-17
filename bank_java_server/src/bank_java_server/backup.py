from google.cloud import storage
import os

# Google Cloud 서비스 계정 키 파일 경로
SERVICE_ACCOUNT_FILE = "C:\\Users\\minch\\eclipse-workspace\\bank_java_server\\src\\bank_java_server\\cloud_key\\white-artwork-444404-k2-0fb17c5d1018.json"

# Google Cloud 버킷 이름
BUCKET_NAME = "2omong_and_marspeople"

# 로컬 디렉토리에서 업로드할 파일 경로
LOCAL_DIRECTORY = "C:\\Users\\minch\\eclipse-workspace\\bank_java_server\\src\\bank_java_server\\bank_data"

def upload_to_gcs(local_directory, bucket_name):
    """지정된 디렉토리의 파일을 GCS로 업로드"""
    # GCS 클라이언트 초기화
    storage_client = storage.Client.from_service_account_json(SERVICE_ACCOUNT_FILE)
    bucket = storage_client.bucket(bucket_name)

    for root, dirs, files in os.walk(local_directory):
        for file_name in files:
            file_path = os.path.join(root, file_name)
            blob = bucket.blob(os.path.relpath(file_path, local_directory))  # GCS 경로 지정
            blob.upload_from_filename(file_path)
            print(f"Uploaded {file_path} to gs://{bucket_name}/{blob.name}")

if __name__ == "__main__":
    upload_to_gcs(LOCAL_DIRECTORY, BUCKET_NAME)
