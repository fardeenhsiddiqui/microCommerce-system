# Future Improvements

## Security
- JWT Authentication (🛠 In Progress)
- Ownership validation
- File type validation after upload
- Virus scanning

## Image Handling
- Pre-signed upload URLs (✅)
- Pre-signed download URLs (✅)
- Thumbnail generation
- WebP conversion
- Image compression

## Scalability
- S3 event notifications
- SQS async processing
- CloudFront CDN integration
- Chunked upload for large files

## Reliability
- Orphan image cleanup (✅)
- Retry mechanism
- Dead letter queue (DLQ)
- Transaction rollback handling

## Monitoring
- Upload metrics
- Processing logs
- Failed upload tracking


# FLOW 1: Pre-signed Upload (Recommended)
1. Create Product
   POST /products

2. Get Upload URL
   POST /api/products/{productId}/images/presigned-url?fileName=image.jpg

   Response:
   {
   "uploadUrl": "...",
   "key": "products/{productId}/uuid_image.jpg"
   }
   exam: (Expired url after 10 min)
   {
   "uploadUrl": "https://e-com-imagestore.s3.ap-south-1.amazonaws.com/products/e5aa6fa2-6a8a-48c7-a5e9-06cb3603360f/808d24c8-acf2-4992-8e33-a5c4382a850c_img-22.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260503T071846Z&X-Amz-SignedHeaders=content-type%3Bhost&X-Amz-Credential=AKIAZVMHF2KOKIXAZEXV%2F20260503%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Expires=600&X-Amz-Signature=37958f6c93071302e709818154aeb7a30f650a385249f79641857900368973b9",
   "key": "products/e5aa6fa2-6a8a-48c7-a5e9-06cb3603360f/808d24c8-acf2-4992-8e33-a5c4382a850c_img-22.jpeg"
   }

3. Upload File to S3 (Client Side)
   PUT {uploadUrl}
   Body: binary file

4. Save Image Metadata
   POST /api/products/{productId}/images
   generateUrl response key = this.key
   {
   "key": "products/{productId}/uuid_image.jpg",
   "label": "front",
   "altText": "front view"
   }


# FLOW 2: Direct Upload (Fallback / Internal)
1. Create Product
   POST /products

2. Upload Image via Backend
   POST /api/files/upload/{productId}
   Body: multipart/form-data

   → Backend uploads file to S3
   → Backend saves metadata in DB
