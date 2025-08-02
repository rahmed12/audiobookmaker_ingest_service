# curl -i -X POST http://localhost:8081/ingest/upload \
#   -H "Content-Type: multipart/form-data" \
#   -F "file=@/workspaces/audiobook-ai-docker-image/myProjects/audiobookmaker_ingest_service/ingest-service/testfile.pdf"

curl -X POST http://localhost:8081/ingest/upload \
  -F 'metadata={"userId":"ab606089-8947-4a16-a384-80d98da40e3b"};type=application/json' \
  -F "file=@/workspaces/audiobook-ai-docker-image/myProjects/audiobookmaker_ingest_service/ingest-service/testfile.pdf;type=application/pdf"

