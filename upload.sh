# curl -i -X POST http://localhost:8081/ingest/upload \
#   -H "Content-Type: multipart/form-data" \
#   -F "file=@/workspaces/audiobook-ai-docker-image/myProjects/audiobookmaker_ingest_service/ingest-service/testfile.pdf"

curl -X POST http://localhost:8081/ingest/upload \
  -F 'metadata={"userId":"17a01d57-7285-425a-996a-153610a5d278"};type=application/json' \
  -F "file=@/workspaces/audiobook-ai-docker-image/myProjects/audiobookmaker_ingest_service/ingest-service/testfile.pdf;type=application/pdf"

